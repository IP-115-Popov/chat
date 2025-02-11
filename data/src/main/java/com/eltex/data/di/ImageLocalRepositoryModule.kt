package com.eltex.data.di

import com.eltex.data.repository.ImageLocalRepositoryImpl
import com.eltex.domain.repository.ImageLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ImageLocalRepositoryModule {
    @Binds
    fun LocalImageSource(impl: ImageLocalRepositoryImpl): ImageLocalRepository
}