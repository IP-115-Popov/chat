package com.eltex.chat.di

import com.eltex.domain.repository.ImageLocalRepository
import com.eltex.domain.repository.ImageNetworkRepository
import com.eltex.domain.repository.AuthDataRepository
import com.eltex.domain.repository.ChatRepository
import com.eltex.domain.repository.SignInNetworkRepository
import com.eltex.domain.repository.TokenRepository
import com.eltex.domain.usecase.SyncAuthDataUseCase
import com.eltex.domain.usecase.SignInUseCase
import com.eltex.domain.repository.ProfileNetworkInfoRepository
import com.eltex.domain.usecase.GetChatListUseCase
import com.eltex.domain.usecase.GetImageUseCase
import com.eltex.domain.usecase.GetProfileInfoUseCase
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

    @Provides
    fun provideGetImageUseCase(
        imageLocalRepository: ImageLocalRepository,
        imageNetworkRepository: ImageNetworkRepository,
    ): GetImageUseCase {
        return GetImageUseCase(
            imageNetworkRepository = imageNetworkRepository,
            imageLocalRepository = imageLocalRepository,
        )
    }

    @Provides
    fun provideGetChatListUseCase(
        chatRepository: ChatRepository,
    ): GetChatListUseCase {
        return GetChatListUseCase(
            chatRepository = chatRepository
        )
    }
}