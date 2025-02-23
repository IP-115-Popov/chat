package com.eltex.data.di.api

import com.eltex.data.api.AvatarApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class AvatarApiModule {
    @Provides
    fun provideAvatarApi(retrofit: Retrofit): AvatarApi =
        retrofit.create<AvatarApi>()
}