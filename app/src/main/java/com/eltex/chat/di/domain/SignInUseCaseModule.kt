package com.eltex.chat.di.domain

import com.eltex.domain.repository.local.AuthDataLocalRepository
import com.eltex.domain.repository.local.HeaderLocalRepository
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
        headerLocalRepository: HeaderLocalRepository,
        authDataLocalRepository: AuthDataLocalRepository,
    ): SignInUseCase {
        return SignInUseCase(
            signInRemoteRepository = signInRemoteRepository,
            headerLocalRepository = headerLocalRepository,
            authDataLocalRepository = authDataLocalRepository
        )
    }
}