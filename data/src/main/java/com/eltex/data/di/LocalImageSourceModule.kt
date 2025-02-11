package com.eltex.data.di

import com.eltex.data.storage.LocalImageSource
import com.eltex.data.storage.LocalImageSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalImageSourceModule {
    @Binds
    fun LocalImageSource(impl: LocalImageSourceImpl): LocalImageSource
}