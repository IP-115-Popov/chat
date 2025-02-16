package com.eltex.data.di

import com.eltex.data.repository.FileLocalRepositoryImpl
import com.eltex.domain.repository.FileLocalRepository
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