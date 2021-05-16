package com.saldi.bitcoinmarketplace.presentation.common.extensions

import com.saldi.bitcoinmarketplace.presentation.charts.common.extensions.applyThousandsSeparator
import org.junit.Assert
import org.junit.Test

/**
 * Created by Sourabh on 14/5/21
 */
class AmountsTest {
    @Test
    fun `Test that an amount with only 3 digits has no thousand separator`() {
        val amountToTest = 100L
        Assert.assertEquals(amountToTest.applyThousandsSeparator(THOUSANDS_SEPARATOR), "100")
    }

    @Test
    fun `Test that an amount with 4 digits has one thousand separator`() {
        val amountToTest = 1000L
        Assert.assertEquals(amountToTest.applyThousandsSeparator(THOUSANDS_SEPARATOR), "1,000")
    }

    @Test
    fun `Test that an amount with 6 digits only has one thousand separator`() {
        val amountToTest = 100000L
        Assert.assertEquals(amountToTest.applyThousandsSeparator(THOUSANDS_SEPARATOR), "100,000")
    }

    @Test
    fun `Test that an amount with 7 digits has two thousand separator`() {
        val amountToTest = 1000000L
        Assert.assertEquals(amountToTest.applyThousandsSeparator(THOUSANDS_SEPARATOR), "1,000,000")
    }

    @Test
    fun `Test that an amount with 9 digits only has two thousand separator`() {
        val amountToTest = 100000000L
        Assert.assertEquals(
            amountToTest.applyThousandsSeparator(THOUSANDS_SEPARATOR),
            "100,000,000"
        )
    }

    @Test
    fun `Test than an amount with 10 digits has three thousand separator`() {
        val amountToTest = 1000000000L
        Assert.assertEquals(
            amountToTest.applyThousandsSeparator(THOUSANDS_SEPARATOR),
            "1,000,000,000"
        )
    }

    companion object {
        const val THOUSANDS_SEPARATOR = ','
    }
}
