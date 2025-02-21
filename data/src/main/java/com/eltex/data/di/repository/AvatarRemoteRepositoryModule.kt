package com.eltex.data.di.repository

import com.eltex.data.repository.remote.AvatarNetworkRepositoryImpl
import com.eltex.domain.repository.remote.AvatarRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AvatarRemoteRepositoryModule {
    @Binds
    fun bindAvatarRemoteRepository(impl: AvatarNetworkRepositoryImpl): AvatarRemoteRepository
}