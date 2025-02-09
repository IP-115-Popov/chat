package com.eltex.chat.feature.authorization.di

import com.eltex.chat.data.repository.SignInRepositoryImpl
import com.eltex.chat.feature.authorization.repository.SignInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SignInRepositoryModule {
    @Binds
    fun bindsUserRepository(impl: SignInRepositoryImpl): SignInRepository
}