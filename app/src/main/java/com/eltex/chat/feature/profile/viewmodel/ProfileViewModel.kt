package com.eltex.chat.feature.profile.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import com.eltex.chat.R
import com.eltex.chat.feature.profile.mappers.ProfileModelToProfileUiMapper
import com.eltex.chat.utils.byteArrayToBitmap
import com.eltex.domain.usecase.SyncAuthDataUseCase
import com.eltex.domain.usecase.remote.GetAvatarUseCase
import com.eltex.domain.usecase.remote.GetImageUseCase
import com.eltex.domain.usecase.remote.GetProfileInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val syncAuthDataUseCase: SyncAuthDataUseCase,
    private val getAvatarUseCase: GetAvatarUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileState>(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        getUser()

        viewModelScope.launch {
            syncAuthDataUseCase().onRight { authData ->
                _state.update {
                    it.copy(
                        token = authData.authToken
                    )
                }
            }

            state.map { it.profileUiModel?.avatarUrl }.distinctUntilChanged().collect { avatarUrl ->
                if (!avatarUrl.isNullOrEmpty()) {
                    loadImage(avatarUrl)
                }
            }
        }
    }


    private fun loadImage(imageUrl: String) {
        setStatus(ProfileStatus.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val subject = state.value.profileUiModel?.username
                val rc_uid = state.value.profileUiModel?.id
                val rc_token = state.value.token

                subject?.let {
                    val avatarRes = getAvatarUseCase(
                        subject = subject, rc_uid = rc_uid ?: "", rc_token = rc_token ?: ""
                    )
                    when (avatarRes) {
                        is Either.Left -> {}
                        is Either.Right -> {
                            val avatar = avatarRes.value.byteArrayToBitmap()
                            _state.update {
                                it.copy(
                                    avatarImg = avatar
                                )
                            }
                        }

                        else -> {}
                    }

                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error loading Avatar", e)
                withContext(Dispatchers.Main) {
                    setStatus(ProfileStatus.Error(context.getString(R.string.data_error)))
                }
            }
        }
    }

    private fun getUser() {
        setStatus(ProfileStatus.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                when (val getProfileResult = getProfileInfoUseCase()) {
                    is Either.Left -> {
                        withContext(Dispatchers.Main) {
                            setStatus(ProfileStatus.Error(context.getString(R.string.data_error)))
                        }
                    }

                    is Either.Right -> {
                        withContext(Dispatchers.Main) {
                            _state.update {
                                it.copy(
                                    profileUiModel = ProfileModelToProfileUiMapper.map(
                                        getProfileResult.value
                                    )
                                )
                            }
                            Log.i("my-log", state.value.profileUiModel.toString())
                            setStatus(ProfileStatus.Idle)
                        }
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error getting user from local storage", e)
                withContext(Dispatchers.Main) {
                    setStatus(ProfileStatus.Error(context.getString(R.string.data_error)))
                }
            }
        }
    }

    private fun setStatus(profileStatus: ProfileStatus) {
        _state.update {
            it.copy(
                status = profileStatus
            )
        }
    }

    fun exitFromProfile() {
        TODO("Not yet implemented")
    }
}