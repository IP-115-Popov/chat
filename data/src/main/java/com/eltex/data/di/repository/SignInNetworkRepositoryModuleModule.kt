package com.eltex.data.di.repository

import com.eltex.data.repository.remote.SignInNetworkRepositoryImpl
import com.eltex.domain.repository.remote.SignInRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SignInNetworkRepositoryModuleModule {
    @Binds
    fun bindsSignInRemoteRepository(impl: SignInNetworkRepositoryImpl): SignInRemoteRepository
}