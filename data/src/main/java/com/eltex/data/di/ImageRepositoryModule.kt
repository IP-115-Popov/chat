package com.eltex.data.di

import com.eltex.data.repository.ImageRepositoryImpl
import com.eltex.domain.feature.profile.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ImageRepositoryModule {
    @Binds
    fun bindImageRepository(impl: ImageRepositoryImpl): ImageRepository
}