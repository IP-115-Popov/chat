package com.eltex.data.di

import com.eltex.data.repository.UsersRemoteRepositoryImpl
import com.eltex.domain.repository.UsersRemoteRepository
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