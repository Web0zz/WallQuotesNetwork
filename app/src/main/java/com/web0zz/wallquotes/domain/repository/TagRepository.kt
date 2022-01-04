package com.web0zz.wallquotes.domain.repository

import com.github.michaelbull.result.Result
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    suspend fun getTags(): Flow<Result<List<Tag>, Failure>>
}
