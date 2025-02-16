package com.eltex.data.di

import com.eltex.data.repository.remote.ImageRemoteRepositoryImpl
import com.eltex.domain.repository.remote.ImageRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ImageRemoteRepositoryModule {
    @Binds
    fun bindImageRemoteRepository(impl: ImageRemoteRepositoryImpl): ImageRemoteRepository
}