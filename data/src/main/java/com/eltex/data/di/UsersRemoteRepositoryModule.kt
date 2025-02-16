package com.eltex.data.di

import com.eltex.data.repository.remote.UsersRemoteRepositoryImpl
import com.eltex.domain.repository.remote.UsersRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UsersRemoteRepositoryModule {
    @Binds
    fun bindUsersRemoteRepositoryModule(impl: UsersRemoteRepositoryImpl): UsersRemoteRepository
}