package com.eltex.data.di.api

import com.eltex.data.api.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = RetrofitFactory.getRetrofit(client)
}