package com.web0zz.wallquotes.domain.repository

import com.github.michaelbull.result.Result
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {
    suspend fun getQuotes(): Flow<Result<List<Quotes>, Failure>>
    suspend fun getByTag(selectedTag: String): Flow<Result<List<Quotes>, Failure>>
    suspend fun insertQuotes(quotes: Quotes): Flow<Result<UseCase.None, Failure>>
    suspend fun updateQuotes(quotes: Quotes): Flow<Result<UseCase.None, Failure>>
    suspend fun deleteQuotes(quotes: Quotes): Flow<Result<UseCase.None, Failure>>
}