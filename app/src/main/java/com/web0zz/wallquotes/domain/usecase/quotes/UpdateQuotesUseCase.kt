package com.web0zz.wallquotes.domain.usecase.quotes

import com.github.michaelbull.result.Result
import com.web0zz.wallquotes.di.QuotesRepositoryImp
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.repository.QuotesRepository
import com.web0zz.wallquotes.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateQuotesUseCase @Inject constructor(
    @QuotesRepositoryImp private val quotesRepository: QuotesRepository,
) : UseCase<UseCase.None, Failure, Quotes>() {
    override suspend fun run(params: Quotes): Flow<Result<None, Failure>> {
        return quotesRepository.updateQuotes(params)
    }
}
