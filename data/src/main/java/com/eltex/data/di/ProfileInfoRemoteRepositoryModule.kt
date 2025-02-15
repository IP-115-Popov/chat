package com.eltex.data.di

import com.eltex.data.repository.ProfileInfoRemoteRepositoryImpl
import com.eltex.domain.repository.ProfileInfoRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProfileInfoRemoteRepositoryModule {
    @Binds
    fun bindProfileInfoRemoteRepository(impl: ProfileInfoRemoteRepositoryImpl): ProfileInfoRemoteRepository
}