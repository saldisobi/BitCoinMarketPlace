package com.saldi.bitcoinmarketplace.features.bitcoin.fakes

import com.saldi.data.features.bitcoin.remote.BitcoinSourceRemote
import com.saldi.domain.common.state.DomainResult
import com.saldi.domain.features.bitcoin.entities.BitcoinValue


/**
 * Created by Sourabh on 14/5/21
 */
class FakeBitcoinErrorRemoteSource : BitcoinSourceRemote {
    override suspend fun fetchBitcoinInformation(
        chartName: String,
        timeSpan: String,
        rollingAverage: String?
    ): DomainResult<BitcoinValue> {
        return DomainResult.Error(com.saldi.domain.common.exception.Failure.ServerError)
    }
}