package com.eltex.chat.di

import com.eltex.domain.feature.autorization.repository.AuthDataRepository
import com.eltex.domain.feature.autorization.repository.SignInNetworkRepository
import com.eltex.domain.feature.autorization.repository.TokenRepository
import com.eltex.domain.feature.autorization.usecase.SyncAuthDataUseCase
import com.eltex.domain.feature.autorization.usecase.SignInUseCase
import com.eltex.domain.feature.profile.repository.ProfileNetworkInfoRepository
import com.eltex.domain.feature.profile.usecase.GetProfileInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideGetProfileInfoUseCase(
        profileNetworkInfoRepository: ProfileNetworkInfoRepository,
        authDataRepository: AuthDataRepository,
    ): GetProfileInfoUseCase {
        return GetProfileInfoUseCase(
            profileNetworkInfoRepository = profileNetworkInfoRepository,
            authDataRepository = authDataRepository
        )
    }

    @Provides
    fun provideSignInUseCase(
        signInNetworkRepository: SignInNetworkRepository,
        tokenRepository: TokenRepository,
        authDataRepository: AuthDataRepository,
    ): SignInUseCase {
        return SignInUseCase(
            signInNetworkRepository = signInNetworkRepository,
            tokenRepository = tokenRepository,
            authDataRepository = authDataRepository
        )
    }

    @Provides
    fun provideSyncAuthDataUseCase(
        authDataRepository: AuthDataRepository,
        tokenRepository: TokenRepository,
    ): SyncAuthDataUseCase {
        return SyncAuthDataUseCase(
            authDataRepository = authDataRepository,
            tokenRepository = tokenRepository,
        )
    }
}