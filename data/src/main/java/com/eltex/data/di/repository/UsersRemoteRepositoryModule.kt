package com.eltex.data.di.repository

import com.eltex.data.repository.remote.UsersNetworkRepositoryImpl
import com.eltex.domain.repository.remote.UsersRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UsersRemoteRepositoryModule {
    @Binds
    fun bindUsersRemoteRepositoryModule(impl: UsersNetworkRepositoryImpl): UsersRemoteRepository
}