package com.eltex.data.di.repository

import com.eltex.data.repository.remote.ImageNetworkRepositoryImpl
import com.eltex.domain.repository.remote.ImageRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ImageRemoteRepositoryModule {
    @Binds
    fun bindImageRemoteRepository(impl: ImageNetworkRepositoryImpl): ImageRemoteRepository
}