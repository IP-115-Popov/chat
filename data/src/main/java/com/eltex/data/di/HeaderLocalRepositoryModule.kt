package com.eltex.data.di

import com.eltex.data.repository.local.HeaderLocalRepositoryImpl
import com.eltex.domain.repository.local.HeaderLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HeaderLocalRepositoryModule {
    @Binds
    fun bindsHeaderLocalRepository(impl: HeaderLocalRepositoryImpl): HeaderLocalRepository
}