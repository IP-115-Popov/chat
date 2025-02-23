package com.eltex.data.di.api

import com.eltex.data.api.MembersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
class MembersApiModule {
    @Provides
    fun provideMembersApi(retrofit: Retrofit): MembersApi =
        retrofit.create<MembersApi>()
}