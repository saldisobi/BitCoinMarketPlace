package com.saldi.data.features.bitcoin.mappers

import com.saldi.data.common.exceptions.EssentialParamMissingException
import com.saldi.data.features.bitcoin.entities.BitcoinDataRaw
import com.saldi.data.features.bitcoin.entities.BitcoinValueRaw
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException


/**
 * Created by Sourabh on 13/5/21
 */
internal class BitcoinDataMapperTest {

    /**
     * Instance of the class that we want to test.
     */
    private lateinit var mapper: BitcoinDataMapper

    /**
     * Setting up what we need for the tests.
     */
    @Before
    fun setUp() {

        // Instantiating the class that we want to test.
        mapper = BitcoinDataMapper()
    }

    @Test
    fun `Test the mapping of the whole object`() {

        // Creating the object that we will use for the mapping.
        val bitcoinMarketPriceInformationRaw = instantiateBitcoinMarketPriceInformationRawObject()

        // Triggering the function that we want to test.
        val bitcoinMarketPriceInformation = mapper.invoke(bitcoinMarketPriceInformationRaw)

        // Assertions
        Assert.assertEquals(
            bitcoinMarketPriceInformation.status,
            bitcoinMarketPriceInformationRaw.status
        )
        Assert.assertEquals(
            bitcoinMarketPriceInformation.name,
            bitcoinMarketPriceInformationRaw.name
        )
        Assert.assertEquals(
            bitcoinMarketPriceInformation.unit,
            bitcoinMarketPriceInformationRaw.unit
        )
        Assert.assertEquals(
            bitcoinMarketPriceInformation.period,
            bitcoinMarketPriceInformationRaw.period
        )
        Assert.assertEquals(
            bitcoinMarketPriceInformation.description,
            bitcoinMarketPriceInformationRaw.description
        )
        Assert.assertEquals(
            bitcoinMarketPriceInformation.values.size,
            bitcoinMarketPriceInformationRaw.values?.size
        )
        Assert.assertEquals(
            bitcoinMarketPriceInformation.values[0].timestamp,
            bitcoinMarketPriceInformationRaw.values?.get(0)?.timestamp
        )
        Assert.assertEquals(
            bitcoinMarketPriceInformation.values[0].amount,
            bitcoinMarketPriceInformationRaw.values?.get(0)?.yAxisValue
        )
    }

    @Test
    fun `Check that the EssentialParamMissingException is thrown when the mandatory params are wrong`() {
        var e: Throwable? = null
        try {
            mapper.invoke(
                BitcoinDataRaw(
                    status = "ok",
                    name = "",
                    unit = "",
                    period = "",
                    description = "",
                    values = emptyList()
                )
            )
        } catch (ex: Throwable) {
            e = ex
        }
        Assert.assertTrue(e is EssentialParamMissingException)
    }


    /**
     * Helper method used to instantiate a [BitcoinDataRaw] object.
     */
    private fun instantiateBitcoinMarketPriceInformationRawObject() =
        BitcoinDataRaw(
            status = "ok",
            name = "Market Price (USD)",
            unit = "USD",
            period = "day",
            description = "Average USD market price across major bitcoin exchanges.",
            values = listOf(BitcoinValueRaw(1L, 1f))
        )
}
