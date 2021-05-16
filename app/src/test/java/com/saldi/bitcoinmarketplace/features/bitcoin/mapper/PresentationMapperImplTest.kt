package com.saldi.bitcoinmarketplace.features.bitcoin.mapper

import com.saldi.bitcoinmarketplace.presentation.charts.mapper.PresentationMapperImpl
import com.saldi.domain.features.bitcoin.entities.BitcoinData
import com.saldi.domain.features.bitcoin.entities.BitcoinValue
import org.junit.Assert
import org.junit.Test

/**
 * Created by Sourabh on 14/5/21
 */
class PresentationMapperImplTest {
    /**
     * Instance of the class that we want to test.
     */
    private val mapper = PresentationMapperImpl()

    @Test
    fun `Test the mapping of the whole object`() {
        val bitcoinMarketPriceInformation = instantiateBitcoinMarketPriceInformationObject(
            listOf(
                BitcoinData(1515283200, 16651.47f),
                BitcoinData(1515715200, 13912.88f)
            )
        )

        val viewEntity = mapper.invoke(bitcoinMarketPriceInformation)
        Assert.assertEquals(viewEntity.entryList[0].x, 1515283200f)
        Assert.assertEquals(viewEntity.entryList[0].y, 16651.47f)
        Assert.assertEquals(viewEntity.entryList[1].x, 1515715200f)
        Assert.assertEquals(viewEntity.entryList[1].y, 13912.88f)
        Assert.assertEquals(viewEntity.xAxisLabelCount, 2)
        Assert.assertEquals(
            viewEntity.description,
            "Average USD market price across major bitcoin exchanges."
        )
        Assert.assertEquals(viewEntity.currency, "USD")
    }

    @Test
    fun `Test the mapping of the xAxisLabelCount property - Case 1`() {
        val bitcoinMarketPriceInformation = instantiateBitcoinMarketPriceInformationObject(
            emptyList()
        )

        val viewEntity = mapper.invoke(bitcoinMarketPriceInformation)

        Assert.assertEquals(viewEntity.xAxisLabelCount, 0)
    }

    @Test
    fun `Test the mapping of the xAxisLabelCount property - Case 2`() {
        val bitcoinMarketPriceInformation = instantiateBitcoinMarketPriceInformationObject(
            listOf(BitcoinData(0, 0f))
        )

        val viewEntity = mapper.invoke(bitcoinMarketPriceInformation)

        Assert.assertEquals(viewEntity.xAxisLabelCount, 1)
    }

    @Test
    fun `Test the mapping of the xAxisLabelCount property - Case 3`() {
        val bitcoinMarketPriceInformation = instantiateBitcoinMarketPriceInformationObject(
            MutableList(2) { BitcoinData(0, 0f) })

        val viewEntity = mapper.invoke(bitcoinMarketPriceInformation)

        Assert.assertEquals(viewEntity.xAxisLabelCount, 2)
    }

    @Test
    fun `Test the mapping of the xAxisLabelCount property - Case 4`() {
        val bitcoinMarketPriceInformation = instantiateBitcoinMarketPriceInformationObject(
            MutableList(3) { BitcoinData(0, 0f) })

        val viewEntity = mapper.invoke(bitcoinMarketPriceInformation)

        Assert.assertEquals(viewEntity.xAxisLabelCount, 3)
    }

    @Test
    fun `Test the mapping of the xAxisLabelCount property - Case 5`() {
        val bitcoinMarketPriceInformation = instantiateBitcoinMarketPriceInformationObject(
            MutableList(4) { BitcoinData(0, 0f) })

        val viewEntity = mapper.invoke(bitcoinMarketPriceInformation)

        Assert.assertEquals(viewEntity.xAxisLabelCount, 4)
    }

    @Test
    fun `Test the mapping of the xAxisLabelCount property - Case 6`() {
        val bitcoinMarketPriceInformation = instantiateBitcoinMarketPriceInformationObject(
            MutableList(5) { BitcoinData(0, 0f) })

        val viewEntity = mapper.invoke(bitcoinMarketPriceInformation)

        Assert.assertEquals(viewEntity.xAxisLabelCount, 4)
    }

    /**
     * Helper method used to instantiate a [BitcoinValue] object.
     */
    private fun instantiateBitcoinMarketPriceInformationObject(values: List<BitcoinData>) =
        BitcoinValue(
            status = "ok",
            name = "Market Price (USD)",
            unit = "USD",
            period = "day",
            description = "Average USD market price across major bitcoin exchanges.",
            values = values
        )
}
