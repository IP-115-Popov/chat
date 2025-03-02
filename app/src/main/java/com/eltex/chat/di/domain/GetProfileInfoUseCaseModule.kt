package com.eltex.chat.di.domain

import com.eltex.domain.repository.remote.ProfileInfoRemoteRepository
import com.eltex.domain.usecase.remote.GetProfileInfoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GetProfileInfoUseCaseModule {
    @Singleton
    @Provides
    fun provideGetProfileInfoUseCase(
        profileInfoRemoteRepository: ProfileInfoRemoteRepository,
    ): GetProfileInfoUseCase {
        return GetProfileInfoUseCase(
            profileInfoRemoteRepository = profileInfoRemoteRepository,
        )
    }
}