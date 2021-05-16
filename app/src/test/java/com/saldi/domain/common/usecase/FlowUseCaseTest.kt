package com.saldi.domain.common.usecase

import com.saldi.domain.common.state.DomainResult
import com.saldi.testcommon.MainCoroutineRule
import com.saldi.testcommon.runBlockingTest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test


/**
 * Created by Sourabh on 13/5/21
 */
@ExperimentalCoroutinesApi
class FlowUseCaseTest {
    // Overrides Dispatchers.Main used in Coroutines
    @get:Rule
    var coroutineRule = MainCoroutineRule()

    private val testDispatcher = coroutineRule.testDispatcher

    @Test
    fun `exception emits Result#Error`() = coroutineRule.runBlockingTest {
        val useCase = ExceptionUseCase(testDispatcher)
        val result = useCase(Unit)
        MatcherAssert.assertThat(
            result.first(),
            CoreMatchers.instanceOf(DomainResult.Error::class.java)
        )
    }

    class ExceptionUseCase(dispatcher: CoroutineDispatcher) : FlowUseCase<Unit, Unit>(dispatcher) {
        override fun execute(parameters: Unit): Flow<DomainResult<Unit>> = flow {
            throw Exception("Test exception")
        }
    }
}