package com.eltex.chat.di

import com.eltex.domain.repository.AuthDataLocalRepository
import com.eltex.domain.repository.ChatCreationRemoteRepository
import com.eltex.domain.repository.ChatMessageRemoteRepository
import com.eltex.domain.repository.ChatRemoteRepository
import com.eltex.domain.repository.HeaderLocalRepository
import com.eltex.domain.repository.ImageLocalRepository
import com.eltex.domain.repository.ImageRemoteRepository
import com.eltex.domain.repository.MessageHistoryRemoteRepository
import com.eltex.domain.repository.ProfileInfoRemoteRepository
import com.eltex.domain.repository.SignInRemoteRepository
import com.eltex.domain.repository.UsersRemoteRepository
import com.eltex.domain.usecase.ConnectWebSocketUseCase
import com.eltex.domain.usecase.CreateChatUseCase
import com.eltex.domain.usecase.GetChatListUseCase
import com.eltex.domain.usecase.GetHistoryChatUseCase
import com.eltex.domain.usecase.GetImageUseCase
import com.eltex.domain.usecase.GetMessageFromChatUseCase
import com.eltex.domain.usecase.GetProfileInfoUseCase
import com.eltex.domain.usecase.GetUsersListUseCase
import com.eltex.domain.usecase.SignInUseCase
import com.eltex.domain.usecase.SyncAuthDataUseCase
import com.eltex.domain.websocket.WebSocketManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideGetProfileInfoUseCase(
        profileInfoRemoteRepository: ProfileInfoRemoteRepository,
        authDataLocalRepository: AuthDataLocalRepository,
    ): GetProfileInfoUseCase {
        return GetProfileInfoUseCase(
            profileInfoRemoteRepository = profileInfoRemoteRepository,
            authDataLocalRepository = authDataLocalRepository
        )
    }

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

    @Provides
    fun provideSyncAuthDataUseCase(
        authDataLocalRepository: AuthDataLocalRepository,
        headerLocalRepository: HeaderLocalRepository,
    ): SyncAuthDataUseCase {
        return SyncAuthDataUseCase(
            authDataLocalRepository = authDataLocalRepository,
            headerLocalRepository = headerLocalRepository,
        )
    }

    @Provides
    fun provideGetImageUseCase(
        imageLocalRepository: ImageLocalRepository,
        imageRemoteRepository: ImageRemoteRepository,
    ): GetImageUseCase {
        return GetImageUseCase(
            imageRemoteRepository = imageRemoteRepository,
            imageLocalRepository = imageLocalRepository,
        )
    }

    @Provides
    fun provideGetChatListUseCase(
        chatRemoteRepository: ChatRemoteRepository,
    ): GetChatListUseCase {
        return GetChatListUseCase(
            chatRemoteRepository = chatRemoteRepository
        )
    }

    @Provides
    fun provideConnectWebSocketUseCase(
        webSocketManager: WebSocketManager,
        authDataLocalRepository: AuthDataLocalRepository,
    ): ConnectWebSocketUseCase {
        return ConnectWebSocketUseCase(
            webSocketManager = webSocketManager,
            authDataLocalRepository = authDataLocalRepository,
        )
    }

    @Provides
    fun provideGetUsersListUseCase(
        usersRemoteRepository: UsersRemoteRepository,
        authDataLocalRepository: AuthDataLocalRepository,
    ): GetUsersListUseCase {
        return GetUsersListUseCase(
            usersRemoteRepository = usersRemoteRepository,
            authDataLocalRepository = authDataLocalRepository,
        )
    }

    @Provides
    fun provideCreateChatUseCase(
        chatCreationRemoteRepository: ChatCreationRemoteRepository,
    ): CreateChatUseCase {
        return CreateChatUseCase(
            chatCreationRemoteRepository = chatCreationRemoteRepository,
        )
    }

    @Provides
    fun provideGetMessageFromChatUseCase(
        chatMessageRemoteRepository: ChatMessageRemoteRepository,
    ): GetMessageFromChatUseCase {
        return GetMessageFromChatUseCase(
            chatMessageRemoteRepository = chatMessageRemoteRepository,
        )
    }

    @Provides
    fun provideGetHistoryChatUseCase(
        messageHistoryRemoteRepository: MessageHistoryRemoteRepository,
    ): GetHistoryChatUseCase {
        return GetHistoryChatUseCase(
            messageHistoryRemoteRepository = messageHistoryRemoteRepository,
        )
    }
}