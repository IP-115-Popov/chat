package com.eltex.chat.feature.profile.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.animation.core.Transition
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.caverock.androidsvg.SVG
import com.caverock.androidsvg.SVGParseException
import com.eltex.chat.R
import com.eltex.chat.feature.profile.mappers.ProfileModelToProfileUiMapper
import com.eltex.domain.usecase.GetImageUseCase
import com.eltex.domain.usecase.GetProfileInfoUseCase
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
    private val getImageUseCase: GetImageUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ProfileState>(ProfileState())
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        getUser()

        viewModelScope.launch {
            state.map { it.profileUiModel?.avatarUrl }
                .distinctUntilChanged()
                .collect { avatarUrl ->
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
                when (val getImageResult = getImageUseCase.execute(imageUrl)) {
                    is Either.Left -> {
                        withContext(Dispatchers.Main) {
                            setStatus(ProfileStatus.Error("Error loading Avatar"))
                        }
                    }

                    is Either.Right -> {
                        val imageBytes = getImageResult.value
                        try {
                            val svg = SVG.getFromInputStream(ByteArrayInputStream(imageBytes))//getFromBytes(imageBytes)

                            // Определение размеров. Если размеры не указаны в SVG, используйте значения по умолчанию
                            val width = (svg.documentWidth?.toInt() ?: 200) // Значение по умолчанию, если ширина не указана
                            val height = (svg.documentHeight?.toInt() ?: 200) // Значение по умолчанию, если высота не указана

                            val newBitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
                            val newCanvas = Canvas(newBitmap)
                            svg.renderToCanvas(newCanvas)

                            withContext(Dispatchers.Main) {
                                _state.update {
                                    it.copy(avatarImg = newBitmap)
                                }
                                setStatus(ProfileStatus.Idle)
                            }
                        } catch (e: SVGParseException) {
                            Log.e("ProfileViewModel", "Error parsing SVG", e)
                            withContext(Dispatchers.Main) {
                                setStatus(ProfileStatus.Error("Error parsing SVG image"))
                            }
                        } catch (e: Exception) {
                            Log.e("ProfileViewModel", "Error rendering SVG", e)
                            withContext(Dispatchers.Main) {
                                setStatus(ProfileStatus.Error("Error rendering SVG image"))
                            }
                        }
                    }

                    else -> {}
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
                when (val getProfileResult = getProfileInfoUseCase.execute()) {
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