package com.web0zz.wallquotes.data.repository

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.web0zz.wallquotes.data.local.TagDao
import com.web0zz.wallquotes.data.repository.mapper.mapTagEntity
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Tag
import com.web0zz.wallquotes.domain.repository.TagRepository
import com.web0zz.wallquotes.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class TagRepositoryImpl @Inject constructor(
    private val tagDao: TagDao,
) : TagRepository {
    override suspend fun getTags(): Flow<Result<List<Tag>, Failure>> = flow {
        val result: Result<List<Tag>, Failure> =
            try {
                val data = tagDao.getAllTags().map {
                    mapTagEntity(it)
                }

                Timber.d("Successfully returned tag data ${data.joinToString(",")}")
                Ok(data)
            } catch (e: Exception) {
                Timber.d("Exception caught ${e.localizedMessage}")
                Err(Failure.UnknownError(Constants.FAILED_READING, e.localizedMessage))
            }

        Timber.d("Tag result emitted ${result::class}")
        emit(result)
    }.flowOn(Dispatchers.IO)
}
