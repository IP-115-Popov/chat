package com.eltex.chat.feature.profile.di

import com.eltex.chat.data.repository.ProfileInfoRepositoryImpl
import com.eltex.chat.feature.profile.repository.ProfileInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ProfileInfoRepositoryModule {
    @Binds
    fun bindProfileInfoRepository(impl: ProfileInfoRepositoryImpl): ProfileInfoRepository
}