package com.eltex.chat.feature.profile.di

import com.eltex.chat.data.repository.ProfileNetworkInfoRepositoryImpl
import com.eltex.chat.feature.profile.repository.ProfileNetworkInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProfileNetworkInfoRepositoryModule {
    @Binds
    fun bindProfileNetworkInfoRepository(impl: ProfileNetworkInfoRepositoryImpl): ProfileNetworkInfoRepository
}