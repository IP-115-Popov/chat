package com.eltex.chat.feature.profile.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.eltex.chat.R
import com.eltex.chat.feature.authorization.repository.AuthDataRepository
import com.eltex.chat.feature.profile.models.ProfileUiModel
import com.eltex.chat.feature.profile.repository.ProfileNetworkInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileNetworkInfoRepository: ProfileNetworkInfoRepository,
    private val authDataRepository: AuthDataRepository,
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileState>(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getUserFromStorage()
            updateUserFromServer()
        }
    }

    private suspend fun getUserFromStorage() {
        try {
            val authData = authDataRepository.getAuthData()

            authData?.let {
                withContext(Dispatchers.Main) {
                    _state.update {
                        it.copy(
                            profileUiModel = ProfileUiModel(
                                id = authData.userId,
                                avatarUrl = authData.avatarUrl,
                                name = authData.name,
                                authToken = authData.authToken,
                            )
                        )
                    }
                    setStatus(ProfileStatus.Idle)
                }
            }
        } catch (e: Exception) {
            Log.e("ProfileViewModel", "Error getting user from local storage", e)
            setStatus(ProfileStatus.Error(context.getString(R.string.data_error)))
        }
    }

    private suspend fun updateUserFromServer() {
        setStatus(ProfileStatus.Loading)
        state.value.profileUiModel?.let { profileUiModel ->
            try {
                val profileInfo =
                    profileNetworkInfoRepository.getProfileInfo(
                        userId = profileUiModel.id,
                        authToken = profileUiModel.authToken,
                        )

                when (profileInfo) {
                    is Either.Left -> {
                        setStatus(ProfileStatus.Error(profileInfo.value))
                    }

                    is Either.Right -> {
                        withContext(Dispatchers.Main) {
                            _state.update {
                                it.copy(
                                    profileUiModel = profileInfo.value
                                )
                            }
                            setStatus(ProfileStatus.Idle)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error getting user from server", e)
                setStatus(ProfileStatus.Error(context.getString(R.string.connection_is_missing)))
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