package com.saldi.domain.common.usecase

import com.saldi.domain.common.exception.Failure
import com.saldi.domain.common.state.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

/**
 * Created by Sourabh on 10/5/21
 *
 * A use case in Clean Architecture represents an execution unit of asynchronous work.
 * A [FlowUseCase] exposes a cold stream of values implemented with Kotlin [Flow].
 * Executes business logic in its execute method and keep posting updates to the result as
 * [DomainResult<R>].
 * Handling an exception (emit [DomainResult.Error] to the result) is the subclasses's responsibility.
 */
abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {
    operator fun invoke(parameters: P): Flow<DomainResult<R>> = execute(parameters)
        .catch { e -> emit(DomainResult.Error(Failure.ServerError)) }
        .flowOn(coroutineDispatcher)

    protected abstract fun execute(parameters: P): Flow<DomainResult<R>>
}