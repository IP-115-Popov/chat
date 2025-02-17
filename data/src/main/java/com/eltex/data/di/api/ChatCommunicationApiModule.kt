package com.eltex.data.di.api

import com.eltex.data.api.AuthorizationApi
import com.eltex.data.api.ChatCommunicationApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class ChatCommunicationApiModule {
    @Provides
    fun provideChatCommunicationApi(retrofit: Retrofit): ChatCommunicationApi =
        retrofit.create<ChatCommunicationApi>()
}