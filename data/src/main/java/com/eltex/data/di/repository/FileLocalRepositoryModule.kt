package com.eltex.data.di.repository

import com.eltex.data.repository.local.FileLocalRepositoryImpl
import com.eltex.domain.repository.local.FileLocalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FileLocalRepositoryModule {
    @Binds
    fun bindFileLocalRepository(impl: FileLocalRepositoryImpl): FileLocalRepository
}