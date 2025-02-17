package com.eltex.data.di.api

import com.eltex.data.api.HistoryChatApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class HistoryChatApiModule {
    @Provides
    fun provideHistoryChatApi(retrofit: Retrofit): HistoryChatApi =
        retrofit.create<HistoryChatApi>()
}