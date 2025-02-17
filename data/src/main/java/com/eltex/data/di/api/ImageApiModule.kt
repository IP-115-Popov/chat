package com.eltex.data.di.api

import com.eltex.data.api.ImageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class ImageApiModule {
    @Provides
    fun provideImageApi(retrofit: Retrofit): ImageApi =
        retrofit.create<ImageApi>()
}