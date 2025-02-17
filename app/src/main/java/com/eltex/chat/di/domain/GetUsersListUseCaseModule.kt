package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.UsersRemoteRepository
import com.eltex.domain.usecase.remote.GetUsersListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GetUsersListUseCaseModule {
    @Provides
    fun provideGetUsersListUseCase(
        usersRemoteRepository: UsersRemoteRepository,
    ): GetUsersListUseCase {
        return GetUsersListUseCase(
            usersRemoteRepository = usersRemoteRepository,
        )
    }
}