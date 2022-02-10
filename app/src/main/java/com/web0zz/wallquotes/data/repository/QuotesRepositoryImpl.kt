package com.web0zz.wallquotes.data.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.web0zz.wallquotes.data.local.QuotesDao
import com.web0zz.wallquotes.data.repository.mapper.mapQuotesEntity
import com.web0zz.wallquotes.data.repository.mapper.mapQuotesToEntity
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.repository.QuotesRepository
import com.web0zz.wallquotes.domain.usecase.UseCase
import com.web0zz.wallquotes.util.Constants.FAILED_DELETE
import com.web0zz.wallquotes.util.Constants.FAILED_INSERT
import com.web0zz.wallquotes.util.Constants.FAILED_READING
import com.web0zz.wallquotes.util.Constants.FAILED_UPDATE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class QuotesRepositoryImpl @Inject constructor(
    private val quotesDao: QuotesDao,
) : QuotesRepository {
    override suspend fun getQuotes(): Flow<Result<List<Quotes>, Failure>> = flow {
        val result: Result<List<Quotes>, Failure> =
            try {
                val data = quotesDao.getAllQuotes().map {
                    mapQuotesEntity(it)
                }

                Timber.d("Successfully returned quotes data ${data.joinToString(",")}")
                Ok(data)
            } catch (e: Exception) {
                Timber.d("Exception caught ${e.localizedMessage}")
                Err(Failure.UnknownError(FAILED_READING, e.localizedMessage))
            }

        Timber.d("Quotes result emitted ${result::class}")
        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun getByTag(selectedTag: String): Flow<Result<List<Quotes>, Failure>> = flow {
        val result: Result<List<Quotes>, Failure> =
            try {
                val data = quotesDao.getByTag(selectedTag.lowercase()).map {
                    mapQuotesEntity(it)
                }

                Timber.d("Successfully returned quotes by tag data ${data.joinToString(",")}")
                Ok(data)
            } catch (e: Exception) {
                Timber.d("Exception caught ${e.localizedMessage}")
                Err(Failure.UnknownError(FAILED_READING, e.localizedMessage))
            }

        Timber.d("Quotes by tag result emitted ${result::class}")
        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun getLikedQuotes(): Flow<Result<List<Quotes>, Failure>> = flow {
        val result: Result<List<Quotes>, Failure> =
            try {
                val data = quotesDao.getLikedQuotes().map {
                    mapQuotesEntity(it)
                }

                Timber.d("Successfully returned liked quotes data ${data.joinToString(",")}")
                Ok(data)
            } catch (e: Exception) {
                Timber.d("Exception caught ${e.localizedMessage}")
                Err(Failure.UnknownError(FAILED_READING, e.localizedMessage))
            }

        Timber.d("Liked quotes result emitted ${result::class}")
        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun insertQuotes(quotes: Quotes): Flow<Result<UseCase.None, Failure>> = flow {
        val result: Result<UseCase.None, Failure> =
            try {
                val data = mapQuotesToEntity(quotes)
                quotesDao.insertQuotes(data)

                Timber.d("Quote inserted to database ${data.id}:${data.authorName}:${data.body}")
                Ok(UseCase.None())
            } catch (e: Exception) {
                Timber.d("Exception caught ${e.localizedMessage}")
                Err(Failure.UnknownError(FAILED_INSERT, e.localizedMessage))
            }

        Timber.d("Insert result emitted ${result::class}")
        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun updateQuotes(quotes: Quotes): Flow<Result<UseCase.None, Failure>> = flow {
        val result: Result<UseCase.None, Failure> =
            try {
                val data = mapQuotesToEntity(quotes)
                quotesDao.updateQuotes(data)

                Timber.d("Quote updated on database ${data.id}:${data.authorName}:${data.body}")
                Ok(UseCase.None())
            } catch (e: Exception) {
                Timber.d("Exception caught ${e.localizedMessage}")
                Err(Failure.UnknownError(FAILED_UPDATE, e.localizedMessage))
            }

        Timber.d("Update result emitted ${result::class}")
        emit(result)
    }.flowOn(Dispatchers.IO)

    override suspend fun deleteQuotes(quotes: Quotes): Flow<Result<UseCase.None, Failure>> = flow {
        val result: Result<UseCase.None, Failure> =
            try {
                val data = mapQuotesToEntity(quotes)
                quotesDao.deleteQuotes(data)

                Timber.d("Quote deleted from database ${data.id}:${data.authorName}:${data.body}")
                Ok(UseCase.None())
            } catch (e: Exception) {
                Timber.d("Exception caught ${e.localizedMessage}")
                Err(Failure.UnknownError(FAILED_DELETE, e.localizedMessage))
            }

        Timber.d("Delete result emitted ${result::class}")
        emit(result)
    }.flowOn(Dispatchers.IO)
}
