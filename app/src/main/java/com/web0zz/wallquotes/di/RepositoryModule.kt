@file:Suppress("unused", "unused", "unused", "unused", "unused")

package com.web0zz.wallquotes.di

import com.web0zz.wallquotes.data.repository.QuotesRepositoryImpl
import com.web0zz.wallquotes.data.repository.TagRepositoryImpl
import com.web0zz.wallquotes.domain.repository.QuotesRepository
import com.web0zz.wallquotes.domain.repository.TagRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @QuotesRepositoryImp
    fun bindQuotesRepository(quotesRepositoryImpl: QuotesRepositoryImpl): QuotesRepository

    @Binds
    @TagRepositoryImp
    fun bindTagRepository(tagRepositoryImpl: TagRepositoryImpl): TagRepository
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class QuotesRepositoryImp

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class TagRepositoryImp
