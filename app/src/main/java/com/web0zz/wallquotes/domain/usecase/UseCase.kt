package com.web0zz.wallquotes.domain.usecase

import com.github.michaelbull.result.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

// Learn more about this implementation
//
// https://github.com/android10/Android-CleanArchitecture-Kotlin
// https://adambennett.dev/2020/05/the-result-monad/
abstract class UseCase<out Type, out Failure, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Flow<Result<Type, Failure>>

    @DelicateCoroutinesApi
    operator fun invoke(
        params: Params,
        scope: CoroutineScope = GlobalScope,
        onResult: (Flow<Result<Type, Failure>>) -> Unit = {}
    ) {
        scope.launch(Dispatchers.Main) {
            val deferred = async(Dispatchers.IO) {
                run(params)
            }
            onResult(deferred.await())
        }
    }

    class None
}