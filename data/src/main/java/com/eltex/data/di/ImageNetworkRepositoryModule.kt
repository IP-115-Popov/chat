package com.eltex.data.di

import com.eltex.data.repository.ImageNetworkRepositoryImpl
import com.eltex.domain.repository.ImageNetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ImageNetworkRepositoryModule {
    @Binds
    fun bindNetworkImageSource(impl: ImageNetworkRepositoryImpl): ImageNetworkRepository
}