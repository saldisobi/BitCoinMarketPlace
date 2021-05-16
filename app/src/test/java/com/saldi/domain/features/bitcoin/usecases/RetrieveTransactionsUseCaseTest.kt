package com.saldi.domain.features.bitcoin.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.saldi.data.features.bitcoin.repositories.BitcoinRepositoryImpl
import com.saldi.domain.common.state.DomainResult
import com.saldi.domain.common.state.data
import com.saldi.domain.repository.TestBitcoinTransactionsSourceRemote
import com.saldi.domain.repository.TestErrorSourceRemote
import com.saldi.testcommon.MainCoroutineRule
import com.saldi.testcommon.data.TestData
import com.saldi.testcommon.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Sourabh on 13/5/21
 */
@ExperimentalCoroutinesApi
class RetrieveTransactionsUseCaseTest {
    class RetrieveTransactionsUseCaseTest {

        // Executes tasks in the Architecture Components in the same thread
        @get:Rule
        var instantTaskExecutorRule = InstantTaskExecutorRule()

        // Overrides Dispatchers.Main used in Coroutines
        @get:Rule
        var coroutineRule = MainCoroutineRule()

        private lateinit var useCase: RetrieveTransactionsUseCase

        @Before
        fun setup() {
            val testBitcoinRepository = BitcoinRepositoryImpl(
                TestBitcoinTransactionsSourceRemote()
            )

            useCase = RetrieveTransactionsUseCase(
                testBitcoinRepository,
                coroutineRule.testDispatcher
            )
        }

        @Test
        fun `returns market price chart data`() = coroutineRule.runBlockingTest {
            val result = useCase(Unit).first { it is DomainResult.Success }
            MatcherAssert.assertThat(
                result.data,
                Is.`is`(CoreMatchers.equalTo(TestData.transactionData))
            )
        }


        @Test
        fun `Check that if there is an error, it is propagated`() = coroutineRule.runBlockingTest {
            val testUserEventRepository = BitcoinRepositoryImpl(
                TestErrorSourceRemote()
            )

            useCase = RetrieveTransactionsUseCase(
                testUserEventRepository,
                coroutineRule.testDispatcher
            )

            val result = useCase(Unit).first { it is DomainResult.Error }

            MatcherAssert.assertThat(
                result,
                Is.`is`(CoreMatchers.equalTo(DomainResult.Error(com.saldi.domain.common.exception.Failure.ServerError)))
            )
        }

    }
}
