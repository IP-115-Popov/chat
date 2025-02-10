package com.eltex.chat.feature.authorization.di

import com.eltex.chat.data.repository.SignInNetworkRepositoryImpl
import com.eltex.chat.feature.authorization.repository.SignInNetworkRepository
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