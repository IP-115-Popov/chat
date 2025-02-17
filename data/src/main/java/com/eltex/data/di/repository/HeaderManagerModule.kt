package com.eltex.data.di.repository

import com.eltex.data.api.HeaderManagerImpl
import com.eltex.domain.HeaderManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface HeaderManagerModule {
    @Binds
    fun bindsHeaderManager(impl: HeaderManagerImpl): HeaderManager
}