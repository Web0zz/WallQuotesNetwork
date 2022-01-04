package com.web0zz.wallquotes.domain.usecase.tag

import com.github.michaelbull.result.Result
import com.web0zz.wallquotes.di.TagRepositoryImp
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Tag
import com.web0zz.wallquotes.domain.repository.TagRepository
import com.web0zz.wallquotes.domain.usecase.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTagUseCase @Inject constructor(
    @TagRepositoryImp private val tagRepository: TagRepository,
) : UseCase<List<Tag>, Failure, UseCase.None>() {
    override suspend fun run(params: None): Flow<Result<List<Tag>, Failure>> {
        return tagRepository.getTags()
    }
}
