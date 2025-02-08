package com.eltex.chat.data.di

import com.eltex.chat.data.api.AuthorizationApi
import com.eltex.chat.data.api.OkHttpClientFactory
import com.eltex.chat.data.api.RetrofitFactory
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
    fun providePostApi(retrofit: Retrofit): AuthorizationApi = retrofit.create<AuthorizationApi>()
}