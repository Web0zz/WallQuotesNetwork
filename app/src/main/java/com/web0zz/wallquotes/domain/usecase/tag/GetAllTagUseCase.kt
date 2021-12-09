package com.web0zz.wallquotes.domain.usecase.tag

import com.github.michaelbull.result.Result
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Tag
import com.web0zz.wallquotes.domain.repository.TagRepository
import com.web0zz.wallquotes.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetAllTagUseCase(
    private val tagRepository: TagRepository,
    mainDispatcher: CoroutineDispatcher
) : UseCase<List<Tag>, Failure, UseCase.None>(mainDispatcher) {
    override suspend fun run(params: None): Flow<Result<List<Tag>, Failure>> {
        return tagRepository.getTags()
    }
}