package com.eltex.chat.di.domain

import com.eltex.domain.HeaderManager
import com.eltex.domain.repository.local.AuthDataLocalRepository
import com.eltex.domain.repository.remote.SignInRemoteRepository
import com.eltex.domain.usecase.remote.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class SignInUseCaseModule {
    @Provides
    fun provideSignInUseCase(
        signInRemoteRepository: SignInRemoteRepository,
        headerManager: HeaderManager,
        authDataLocalRepository: AuthDataLocalRepository,
    ): SignInUseCase {
        return SignInUseCase(
            signInRemoteRepository = signInRemoteRepository,
            headerManager = headerManager,
            authDataLocalRepository = authDataLocalRepository
        )
    }
}