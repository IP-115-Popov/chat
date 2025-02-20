package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.UsersRemoteRepository
import com.eltex.domain.usecase.remote.GetUserInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class GetUserInfoUseCaseModule {
    @Provides
    fun provideGetUserInfoUseCaseModule(
        usersRemoteRepository: UsersRemoteRepository,
    ): GetUserInfoUseCase {
        return GetUserInfoUseCase(
            usersRemoteRepository = usersRemoteRepository,
        )
    }
}