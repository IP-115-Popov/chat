package com.eltex.data.di

import com.eltex.data.repository.SignInNetworkRepositoryImpl
import com.eltex.domain.repository.SignInNetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SignInNetworkRepositoryModule {
    @Binds
    fun bindsSignInNetworkRepository(impl: SignInNetworkRepositoryImpl): SignInNetworkRepository
}