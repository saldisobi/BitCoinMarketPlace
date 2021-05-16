package com.saldi.bitcoinmarketplace.features.bitcoin.fakes

import com.saldi.data.features.bitcoin.remote.BitcoinSourceRemote
import com.saldi.domain.common.state.DomainResult
import com.saldi.domain.features.bitcoin.entities.BitcoinValue
import com.saldi.testcommon.data.TestData


/**
 * Created by Sourabh on 14/5/21
 */
class FakeBitcoinTradeVolumeRemoteSource : BitcoinSourceRemote {
    override suspend fun fetchBitcoinInformation(
        chartName: String,
        timeSpan: String,
        rollingAverage: String?
    ): DomainResult<BitcoinValue> {
        return DomainResult.Success(TestData.tradeVolumeData)
    }
}