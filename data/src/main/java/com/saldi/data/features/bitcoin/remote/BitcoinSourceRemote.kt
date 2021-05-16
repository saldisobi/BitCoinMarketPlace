package com.saldi.data.features.bitcoin.remote

import com.saldi.domain.common.state.DomainResult
import com.saldi.domain.features.bitcoin.entities.BitcoinValue

/**
 * Created by Sourabh on 10/5/21
 */
interface BitcoinSourceRemote {
    suspend fun fetchBitcoinInformation(
        chartName: String,
        timeSpan: String,
        rollingAverage: String?
    ): DomainResult<BitcoinValue>

}