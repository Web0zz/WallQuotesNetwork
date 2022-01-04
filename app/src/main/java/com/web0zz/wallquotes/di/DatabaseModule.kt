package com.web0zz.wallquotes.di

import android.app.Application
import com.web0zz.wallquotes.data.local.QuotesDao
import com.web0zz.wallquotes.data.local.QuotesDatabase
import com.web0zz.wallquotes.data.local.TagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideQuotesDatabase(application: Application): QuotesDatabase =
        QuotesDatabase.getInstance(application)

    @Singleton
    @Provides
    fun provideQuotesDao(database: QuotesDatabase): QuotesDao = database.quotesDao()

    @Singleton
    @Provides
    fun provideCategoryDao(database: QuotesDatabase): TagDao = database.tagDao()
}
