package com.eltex.data.di

import com.eltex.data.api.AuthorizationApi
import com.eltex.data.api.ChatCreationApi
import com.eltex.data.api.HistoryChatApi
import com.eltex.data.api.ImageApi
import com.eltex.data.api.OkHttpClientFactory
import com.eltex.data.api.ProfileInfoApi
import com.eltex.data.api.RetrofitFactory
import com.eltex.data.api.UsersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClientFactory.getOkHttpClient()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = RetrofitFactory.getRetrofit(client)

    @Provides
    fun provideAuthorizationApi(retrofit: Retrofit): AuthorizationApi =
        retrofit.create<AuthorizationApi>()

    @Provides
    fun provideProfileInfoApi(retrofit: Retrofit): ProfileInfoApi =
        retrofit.create<ProfileInfoApi>()

    @Provides
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create<UsersApi>()

    @Provides
    fun provideChatCreationApi(retrofit: Retrofit): ChatCreationApi =
        retrofit.create<ChatCreationApi>()

    @Provides
    fun provideHistoryChatApi(retrofit: Retrofit): HistoryChatApi =
        retrofit.create<HistoryChatApi>()

    @Provides
    fun provideImageApi(retrofit: Retrofit): ImageApi =
        retrofit.create<ImageApi>()
}