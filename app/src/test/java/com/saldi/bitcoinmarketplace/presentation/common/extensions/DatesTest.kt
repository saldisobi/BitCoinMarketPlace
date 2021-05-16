package com.saldi.bitcoinmarketplace.presentation.common.extensions

import com.saldi.bitcoinmarketplace.presentation.charts.common.extensions.getFormattedDateFromTimestamp
import com.saldi.bitcoinmarketplace.presentation.common.constants.Constants.LONG_DATE_FORMAT
import com.saldi.bitcoinmarketplace.presentation.common.constants.Constants.SHORT_DATE_FORMAT
import org.junit.Assert
import org.junit.Test

/**
 * Created by Sourabh on 14/5/21
 */
class DatesTest {

    @Test
    fun `Test to convert a timestamp and check that it went ok - Case 1`() {
        val timestampToTest = 1515715200L
        val shortFormattedDate = timestampToTest.getFormattedDateFromTimestamp(SHORT_DATE_FORMAT)
        val longFormattedDate = timestampToTest.getFormattedDateFromTimestamp(LONG_DATE_FORMAT)
        Assert.assertEquals(shortFormattedDate, "12 Jan")
        Assert.assertEquals(longFormattedDate, "2018-01-12")
    }

    @Test
    fun `Test to convert a timestamp and check that it went ok - Case 2`() {
        val timestampToTest = 1520380800L
        val shortFormattedDate = timestampToTest.getFormattedDateFromTimestamp(SHORT_DATE_FORMAT)
        val longFormattedDate = timestampToTest.getFormattedDateFromTimestamp(LONG_DATE_FORMAT)
        Assert.assertEquals(shortFormattedDate, "07 Mar")
        Assert.assertEquals(longFormattedDate, "2018-03-07")
    }

    @Test
    fun `Test to convert a timestamp and check that it went ok - Case 3`() {
        val timestampToTest = 1523664000L
        val shortFormattedDate = timestampToTest.getFormattedDateFromTimestamp(SHORT_DATE_FORMAT)
        val longFormattedDate = timestampToTest.getFormattedDateFromTimestamp(LONG_DATE_FORMAT)
        Assert.assertEquals(shortFormattedDate, "14 Apr")
        Assert.assertEquals(longFormattedDate, "2018-04-14")
    }

    @Test
    fun `Test to convert a timestamp and check that it went ok - Case 4`() {
        val timestampToTest = 1534982400L
        val shortFormattedDate = timestampToTest.getFormattedDateFromTimestamp(SHORT_DATE_FORMAT)
        val longFormattedDate = timestampToTest.getFormattedDateFromTimestamp(LONG_DATE_FORMAT)
        Assert.assertEquals(shortFormattedDate, "23 Aug")
        Assert.assertEquals(longFormattedDate, "2018-08-23")
    }

    @Test
    fun `Test to convert a timestamp and check that it went ok - Case 5`() {
        val timestampToTest = 1541721600L
        val shortFormattedDate = timestampToTest.getFormattedDateFromTimestamp(SHORT_DATE_FORMAT)
        val longFormattedDate = timestampToTest.getFormattedDateFromTimestamp(LONG_DATE_FORMAT)
        Assert.assertEquals(shortFormattedDate, "09 Nov")
        Assert.assertEquals(longFormattedDate, "2018-11-09")
    }
}
