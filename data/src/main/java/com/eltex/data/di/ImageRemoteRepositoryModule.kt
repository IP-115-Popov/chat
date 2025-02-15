package com.eltex.data.di

import com.eltex.data.repository.ImageRemoteRepositoryImpl
import com.eltex.domain.repository.ImageRemoteRepository
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