package com.eltex.data.di.api

import com.eltex.data.api.ChatApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class ChatApiModule {
    @Provides
    fun provideChatApi(retrofit: Retrofit): ChatApi =
        retrofit.create<ChatApi>()
}