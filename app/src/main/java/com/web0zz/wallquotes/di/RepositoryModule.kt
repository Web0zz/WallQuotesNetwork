package com.web0zz.wallquotes.di

import com.web0zz.wallquotes.data.local.QuotesDao
import com.web0zz.wallquotes.data.repository.QuotesRepositoryImpl
import com.web0zz.wallquotes.domain.repository.QuotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Provides
    @Singleton
    fun provideQuotesRepository(quotesDao: QuotesDao): QuotesRepository =
        QuotesRepositoryImpl(quotesDao)
}
