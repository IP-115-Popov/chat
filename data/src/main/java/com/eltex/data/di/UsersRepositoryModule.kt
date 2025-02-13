package com.eltex.data.di

import com.eltex.data.repository.UsersNetworkRepositoryImpl
import com.eltex.domain.repository.UsersNetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UsersNetworkRepositoryModule {
    @Binds
    fun bindUsersNetworkRepository(impl: UsersNetworkRepositoryImpl): UsersNetworkRepository
}