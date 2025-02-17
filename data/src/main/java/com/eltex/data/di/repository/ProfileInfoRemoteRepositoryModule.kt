package com.eltex.data.di.repository

import com.eltex.data.repository.remote.ProfileInfoNetworkRepositoryImpl
import com.eltex.domain.repository.remote.ProfileInfoRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProfileInfoRemoteRepositoryModule {
    @Binds
    fun bindProfileInfoRemoteRepository(impl: ProfileInfoNetworkRepositoryImpl): ProfileInfoRemoteRepository
}