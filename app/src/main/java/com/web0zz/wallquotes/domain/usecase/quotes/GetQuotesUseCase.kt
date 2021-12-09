package com.web0zz.wallquotes.domain.usecase.quotes

import com.github.michaelbull.result.Result
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.repository.QuotesRepository
import com.web0zz.wallquotes.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetQuotesUseCase(
    private val quotesRepository: QuotesRepository,
    mainDispatcher: CoroutineDispatcher
) : UseCase<List<Quotes>, Failure, UseCase.None>(mainDispatcher) {
    override suspend fun run(params: None): Flow<Result<List<Quotes>, Failure>> {
        return quotesRepository.getQuotes()
    }
}