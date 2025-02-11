package com.eltex.data.di

import com.eltex.data.repository.ProfileNetworkInfoRepositoryImpl
import com.eltex.domain.repository.ProfileNetworkInfoRepository
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