package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.UsersRemoteRepository
import com.eltex.domain.usecase.remote.GetUsersListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GetUsersListUseCaseModule {
    @Singleton
    @Provides
    fun provideGetUsersListUseCase(
        usersRemoteRepository: UsersRemoteRepository,
    ): GetUsersListUseCase {
        return GetUsersListUseCase(
            usersRemoteRepository = usersRemoteRepository,
        )
    }
}