package com.saldi.data.features.bitcoin.remote

import com.google.common.truth.Truth.assertThat
import com.saldi.data.features.bitcoin.mappers.BitcoinDataMapper
import com.saldi.domain.common.exception.Failure
import com.saldi.domain.common.state.DomainResult
import com.saldi.domain.features.bitcoin.entities.BitcoinData
import com.saldi.domain.features.bitcoin.entities.BitcoinValue
import com.saldi.testcommon.BitcoinRequestDispatcher
import com.saldi.testcommon.UrlConstants.MARKET_PRICE_CHART
import com.saldi.testcommon.UrlConstants.MARKET_PRICE_REQUEST
import com.saldi.testcommon.UrlConstants.TIME_SPAN
import com.saldi.testcommon.UrlConstants.UNKNOWN_CHART
import com.saldi.testcommon.makeTestApiService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test


/**
 * Created by Sourabh on 13/5/21
 */
class BitcoinSourceRemoteImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var bitcoinSourceRemote: BitcoinSourceRemote
    private val bitcoinDataMapper = BitcoinDataMapper()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = BitcoinRequestDispatcher().RequestDispatcher()
        mockWebServer.start()
        bitcoinSourceRemote =
            BitcoinSourceRemoteImpl(makeTestApiService(mockWebServer), bitcoinDataMapper)
    }

    @Test
    fun `check that fetchBitcoinInformation returns BitcoinValue`() = runBlocking {
        val bitcoinValue: DomainResult<BitcoinValue> = bitcoinSourceRemote.fetchBitcoinInformation(
            MARKET_PRICE_CHART,
            TIME_SPAN, null
        )
        assertThat(bitcoinValue).isInstanceOf(DomainResult.Success::class.java)
        bitcoinValue as DomainResult.Success
        val values: List<BitcoinData> = bitcoinValue.data.values
        assertThat(values).isNotEmpty()
    }

    @Test
    fun `check that fetchBitcoinInformation returns correct data`() = runBlocking {
        val bitcoinValue: DomainResult<BitcoinValue> = bitcoinSourceRemote.fetchBitcoinInformation(
            MARKET_PRICE_CHART,
            TIME_SPAN, null
        )
        assertThat(bitcoinValue).isInstanceOf(DomainResult.Success::class.java)
        bitcoinValue as DomainResult.Success
        val bitcoinValueData: BitcoinValue = bitcoinValue.data
        val values: List<BitcoinData> = bitcoinValue.data.values
        val firstData: BitcoinData = values.first()
        assertThat(bitcoinValueData.status).isEqualTo("ok")
        assertThat(bitcoinValueData.name).isEqualTo("Market Price (USD)")
        assertThat(bitcoinValueData.unit).isEqualTo("USD")
        assertThat(bitcoinValueData.period).isEqualTo("day")
        assertThat(bitcoinValueData.description).isEqualTo("Average USD market price across major bitcoin exchanges.")
        assertThat(firstData.amount).isEqualTo(59834.74.toFloat())
        assertThat(firstData.timestamp).isEqualTo(1618272000L)
    }

    @Test
    fun `check that calling fetchBitcoinInformation makes request to correct path`() = runBlocking {
        bitcoinSourceRemote.fetchBitcoinInformation(
            MARKET_PRICE_CHART,
            TIME_SPAN, null
        )
        assertThat(MARKET_PRICE_REQUEST)
            .isEqualTo(mockWebServer.takeRequest().path)
    }

    @Test
    fun `check that calling fetchBitcoinInformation makes a GET request`() = runBlocking {
        bitcoinSourceRemote.fetchBitcoinInformation(
            MARKET_PRICE_CHART,
            TIME_SPAN, null
        )
        assertThat("GET $MARKET_PRICE_REQUEST HTTP/1.1")
            .isEqualTo(mockWebServer.takeRequest().requestLine)
    }

    @Test
    fun `check that fetchBitcoinInformation returns error when wrong chart name`() =
        runBlocking {
            val bitcoinValue: DomainResult<BitcoinValue> =
                bitcoinSourceRemote.fetchBitcoinInformation(
                    UNKNOWN_CHART,
                    TIME_SPAN, null
                )
            assertThat(bitcoinValue).isInstanceOf(DomainResult.Error::class.java)
            bitcoinValue as DomainResult.Error
            assertThat(bitcoinValue.failure).isInstanceOf(Failure.ServerError::class.java)
        }

    @Test
    fun `check that fetchBitcoinInformation returns proper error on bad response from server`() =
        runBlocking {
            mockWebServer.dispatcher = BitcoinRequestDispatcher().BadRequestDispatcher()
            val domainResult: DomainResult<BitcoinValue> =
                bitcoinSourceRemote.fetchBitcoinInformation(
                    MARKET_PRICE_CHART,
                    TIME_SPAN, null
                )
            assertThat(domainResult).isInstanceOf(DomainResult.Error::class.java)
            domainResult as DomainResult.Error
            assertThat(domainResult.failure).isInstanceOf(Failure.ServerError::class.java)
        }

    @Test
    fun `check that fetchBitcoinInformation returns proper error on server error`() =
        runBlocking {
            mockWebServer.dispatcher = BitcoinRequestDispatcher().ErrorRequestDispatcher()
            val domainResult: DomainResult<BitcoinValue> =
                bitcoinSourceRemote.fetchBitcoinInformation(
                    MARKET_PRICE_CHART,
                    TIME_SPAN, null
                )
            assertThat(domainResult).isInstanceOf(DomainResult.Error::class.java)
            domainResult as DomainResult.Error
            assertThat(domainResult.failure).isInstanceOf(Failure.ServerError::class.java)
        }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}