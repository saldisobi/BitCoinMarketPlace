package com.saldi.bitcoinmarketplace.features.bitcoin.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.google.common.truth.Truth
import com.saldi.bitcoinmarketplace.features.bitcoin.fakes.*
import com.saldi.bitcoinmarketplace.presentation.charts.common.streams.StreamState
import com.saldi.bitcoinmarketplace.presentation.charts.entities.BitcoinPresentationViewEntity
import com.saldi.bitcoinmarketplace.presentation.charts.mapper.PresentationMapperImpl
import com.saldi.bitcoinmarketplace.presentation.charts.viewmodel.BitcoinChartViewModel
import com.saldi.bitcoinmarketplace.presentation.charts.viewmodel.BitcoinMetrics
import com.saldi.data.features.bitcoin.remote.BitcoinSourceRemote
import com.saldi.data.features.bitcoin.repositories.BitcoinRepositoryImpl
import com.saldi.domain.features.bitcoin.usecases.RetrieveBitcoinMarketPriceUseCase
import com.saldi.domain.features.bitcoin.usecases.RetrieveBitcoinTradeVolumeUseCase
import com.saldi.domain.features.bitcoin.usecases.RetrieveTransactionsUseCase
import com.saldi.testcommon.LiveDataTestUtil.getValue
import com.saldi.testcommon.MainCoroutineRule
import com.saldi.testcommon.data.TestData
import com.saldi.testcommon.runBlockingTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.Rule
import org.junit.Test

/**
 * Created by Sourabh on 14/5/21
 */
@ExperimentalCoroutinesApi
class BitcoinChartViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val testDispatcher = mainCoroutineRule.testDispatcher

    @Test
    fun `check fetchMarketPrice loads correct data`() = mainCoroutineRule.runBlockingTest {
        // Create ViewModel with the use case and load the feed.
        val viewModel = createBitcoinChartViewModel(FakeBitcoinRemoteSource())

        viewModel.fetchBitcoinInformation(BitcoinMetrics.MarketPrice)

        val bitcoinMarketPriceInformationObservable =
            getValue(viewModel.bitcoinMarketPriceInformationLiveData)

        Truth.assertThat(bitcoinMarketPriceInformationObservable)
            .isInstanceOf(StreamState.Retrieved::class.java)
        val retrieved: StreamState.Retrieved =
            bitcoinMarketPriceInformationObservable as StreamState.Retrieved

        val bitcoinValue: BitcoinPresentationViewEntity =
            retrieved.content as BitcoinPresentationViewEntity

        val mappedData = PresentationMapperImpl().invoke(TestData.marketPriceData)

        val firstEntry = bitcoinValue.entryList.first()
        Truth.assertThat(bitcoinValue.description).isEqualTo(mappedData.description)
        Truth.assertThat(bitcoinValue.xAxisLabelCount).isEqualTo(mappedData.xAxisLabelCount)
        Truth.assertThat(bitcoinValue.currency).isEqualTo(mappedData.currency)
        Truth.assertThat(firstEntry.x).isEqualTo(mappedData.entryList[0].x)
        Truth.assertThat(firstEntry.y).isEqualTo(mappedData.entryList[0].y)


        viewModel.viewModelScope.cancel()
        // Cancel is not synchronous so we need to wait for it to avoid leaks.
        mainCoroutineRule.testDispatcher.advanceUntilIdle()
    }

    @Test
    fun `check fetchMarketPrice error loads error state`() = mainCoroutineRule.runBlockingTest {
        // Create ViewModel with a use case that returns an error


        val viewModel = createBitcoinChartViewModel(FakeBitcoinErrorRemoteSource())


        // Verify that an error was caught
        val errorMessageObservable =
            getValue(viewModel.bitcoinMarketPriceInformationLiveData)


        Truth.assertThat(errorMessageObservable)
            .isInstanceOf(StreamState.Failed::class.java)
        // Must cancel because there's a flow in [GetConferenceStateUseCase] that never finishes.
        viewModel.viewModelScope.cancel()

        // Cancel is not synchronous so we need to wait for it to avoid leaks.
        mainCoroutineRule.testDispatcher.advanceUntilIdle()
    }


    @Test
    fun `check fetchMarketPrice  data error loads empty state`() {

        val viewModel = createBitcoinChartViewModel(FakeBitcoinEmptyRemoteSource())


        // Verify that an error was caught
        val errorMessageObservable =
            getValue(viewModel.bitcoinMarketPriceInformationLiveData)


        Truth.assertThat(errorMessageObservable)
            .isInstanceOf(StreamState.Empty::class.java)
        // Must cancel because there's a flow in [GetConferenceStateUseCase] that never finishes.
        viewModel.viewModelScope.cancel()

        // Cancel is not synchronous so we need to wait for it to avoid leaks.
        mainCoroutineRule.testDispatcher.advanceUntilIdle()

    }

    @Test
    fun `check fetchTransactions loads correct data`() = mainCoroutineRule.runBlockingTest {
        // Create ViewModel with the use case and load the feed.
        val viewModel = createBitcoinChartViewModel(FakeBitcoinTransactionsRemoteSource())

        viewModel.fetchBitcoinInformation(BitcoinMetrics.Transactions)

        val bitcoinMarketPriceInformationObservable =
            getValue(viewModel.bitcoinMarketPriceInformationLiveData)

        Truth.assertThat(bitcoinMarketPriceInformationObservable)
            .isInstanceOf(StreamState.Retrieved::class.java)
        val retrieved: StreamState.Retrieved =
            bitcoinMarketPriceInformationObservable as StreamState.Retrieved

        val bitcoinValue: BitcoinPresentationViewEntity =
            retrieved.content as BitcoinPresentationViewEntity

        val mappedData = PresentationMapperImpl().invoke(TestData.transactionData)

        val firstEntry = bitcoinValue.entryList.first()
        Truth.assertThat(bitcoinValue.description).isEqualTo(mappedData.description)
        Truth.assertThat(bitcoinValue.xAxisLabelCount).isEqualTo(mappedData.xAxisLabelCount)
        Truth.assertThat(bitcoinValue.currency).isEqualTo(mappedData.currency)
        Truth.assertThat(firstEntry.x).isEqualTo(mappedData.entryList[0].x)
        Truth.assertThat(firstEntry.y).isEqualTo(mappedData.entryList[0].y)


        viewModel.viewModelScope.cancel()
        // Cancel is not synchronous so we need to wait for it to avoid leaks.
        mainCoroutineRule.testDispatcher.advanceUntilIdle()
    }

    @Test
    fun `check fetchTradeVolume loads correct data`() = mainCoroutineRule.runBlockingTest {
        // Create ViewModel with the use case and load the feed.
        val viewModel = createBitcoinChartViewModel(FakeBitcoinTradeVolumeRemoteSource())

        viewModel.fetchBitcoinInformation(BitcoinMetrics.TradeVolume)

        val bitcoinMarketPriceInformationObservable =
            getValue(viewModel.bitcoinMarketPriceInformationLiveData)

        Truth.assertThat(bitcoinMarketPriceInformationObservable)
            .isInstanceOf(StreamState.Retrieved::class.java)
        val retrieved: StreamState.Retrieved =
            bitcoinMarketPriceInformationObservable as StreamState.Retrieved

        val bitcoinValue: BitcoinPresentationViewEntity =
            retrieved.content as BitcoinPresentationViewEntity

        val mappedData = PresentationMapperImpl().invoke(TestData.tradeVolumeData)

        val firstEntry = bitcoinValue.entryList.first()
        Truth.assertThat(bitcoinValue.description).isEqualTo(mappedData.description)
        Truth.assertThat(bitcoinValue.xAxisLabelCount).isEqualTo(mappedData.xAxisLabelCount)
        Truth.assertThat(bitcoinValue.currency).isEqualTo(mappedData.currency)
        Truth.assertThat(firstEntry.x).isEqualTo(mappedData.entryList[0].x)
        Truth.assertThat(firstEntry.y).isEqualTo(mappedData.entryList[0].y)


        viewModel.viewModelScope.cancel()
        // Cancel is not synchronous so we need to wait for it to avoid leaks.
        mainCoroutineRule.testDispatcher.advanceUntilIdle()
    }


    private fun createBitcoinChartViewModel(bitcoinRemoteSourceRemote: BitcoinSourceRemote): BitcoinChartViewModel {
        val bitcoinRepository = BitcoinRepositoryImpl(bitcoinRemoteSourceRemote)

        return BitcoinChartViewModel(
            RetrieveBitcoinMarketPriceUseCase(bitcoinRepository, testDispatcher),
            RetrieveBitcoinTradeVolumeUseCase(bitcoinRepository, testDispatcher),
            RetrieveTransactionsUseCase(bitcoinRepository, testDispatcher),
            PresentationMapperImpl()
        )
    }


}
