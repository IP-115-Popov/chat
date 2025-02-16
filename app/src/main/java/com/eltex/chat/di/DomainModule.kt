package com.eltex.chat.di

import com.eltex.domain.repository.local.AuthDataLocalRepository
import com.eltex.domain.repository.remote.ChatCreationRemoteRepository
import com.eltex.domain.repository.remote.ChatMessageRemoteRepository
import com.eltex.domain.repository.remote.ChatRemoteRepository
import com.eltex.domain.repository.local.HeaderLocalRepository
import com.eltex.domain.repository.local.FileLocalRepository
import com.eltex.domain.repository.remote.ImageRemoteRepository
import com.eltex.domain.repository.remote.MessageHistoryRemoteRepository
import com.eltex.domain.repository.remote.ProfileInfoRemoteRepository
import com.eltex.domain.repository.remote.SignInRemoteRepository
import com.eltex.domain.repository.remote.UsersRemoteRepository
import com.eltex.domain.usecase.ConnectWebSocketUseCase
import com.eltex.domain.usecase.remote.CreateChatUseCase
import com.eltex.domain.usecase.remote.GetChatListUseCase
import com.eltex.domain.usecase.remote.GetHistoryChatUseCase
import com.eltex.domain.usecase.remote.GetImageUseCase
import com.eltex.domain.usecase.remote.GetMessageFromChatUseCase
import com.eltex.domain.usecase.remote.GetProfileInfoUseCase
import com.eltex.domain.usecase.remote.GetUsersListUseCase
import com.eltex.domain.usecase.remote.LoadDocumentUseCase
import com.eltex.domain.usecase.remote.SignInUseCase
import com.eltex.domain.usecase.SyncAuthDataUseCase
import com.eltex.domain.usecase.local.LoadFromCacheFileUseCase
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
    ): GetProfileInfoUseCase {
        return GetProfileInfoUseCase(
            profileInfoRemoteRepository = profileInfoRemoteRepository,
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
        imageRemoteRepository: ImageRemoteRepository,
    ): GetImageUseCase {
        return GetImageUseCase(
            imageRemoteRepository = imageRemoteRepository,
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
    ): GetUsersListUseCase {
        return GetUsersListUseCase(
            usersRemoteRepository = usersRemoteRepository,
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

    @Provides
    fun provideLoadDocumentUseCase(
        imageRemoteRepository: ImageRemoteRepository,
        fileLocalRepository: FileLocalRepository,
    ): LoadDocumentUseCase {
        return LoadDocumentUseCase(
            imageRemoteRepository = imageRemoteRepository,
            fileLocalRepository = fileLocalRepository,
        )
    }

    @Provides
    fun provideLoadFromCacheFileUseCase(
        fileLocalRepository: FileLocalRepository,
    ): LoadFromCacheFileUseCase {
        return LoadFromCacheFileUseCase(
            fileLocalRepository = fileLocalRepository,
        )
    }
}