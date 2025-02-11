package com.eltex.data.di

import com.eltex.data.repository.ImageLocalRepositoryImpl
import com.eltex.domain.feature.profile.repository.ImageLocalRepository
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