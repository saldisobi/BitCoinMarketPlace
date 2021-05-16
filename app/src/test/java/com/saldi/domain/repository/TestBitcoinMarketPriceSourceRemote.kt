package com.saldi.domain.repository

import com.saldi.data.features.bitcoin.remote.BitcoinSourceRemote
import com.saldi.domain.common.state.DomainResult
import com.saldi.domain.features.bitcoin.entities.BitcoinValue
import com.saldi.testcommon.data.TestData


/**
 * Created by Sourabh on 13/5/21
 */
class TestBitcoinMarketPriceSourceRemote : BitcoinSourceRemote {
    override suspend fun fetchBitcoinInformation(
        chartName: String,
        timeSpan: String,
        rollingAverage: String?
    ): DomainResult<BitcoinValue> {
        return DomainResult.Success(TestData.marketPriceData)
    }
}