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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TagRepositoryImpl(
    private val tagDao: TagDao
) : TagRepository {
    override suspend fun getTags(): Flow<Result<List<Tag>, Failure>> = flow {
        val result: Result<List<Tag>, Failure> =
            try {
                val data = tagDao.getAllTags().map {
                    mapTagEntity(it)
                }

                Ok(data)
            } catch (e: Exception) {
                Err(Failure.UnknownError(Constants.FAILED_READING, e.localizedMessage))
            }

        emit(result)
    }
}