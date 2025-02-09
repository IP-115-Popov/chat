package com.eltex.chat.feature.authorization.di

import com.eltex.chat.data.repository.UserRepositoryImpl
import com.eltex.chat.feature.authorization.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UserRepositoryModule {
    @Binds
    fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository
}