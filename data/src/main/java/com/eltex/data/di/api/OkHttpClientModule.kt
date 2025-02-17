package com.eltex.data.di.api

import com.eltex.data.api.OkHttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class OkHttpClientModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClientFactory.getOkHttpClient()
}