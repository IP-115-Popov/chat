package com.eltex.data.di

import com.eltex.data.repository.HeaderRepositoryImpl
import com.eltex.domain.repository.HeaderRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HeaderRepositoryModule {
    @Binds
    fun bindsHeaderRepository(impl: HeaderRepositoryImpl): HeaderRepository
}