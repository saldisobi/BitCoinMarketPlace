package com.saldi.bitcoinmarketplace.presentation.testutil

import com.saldi.bitcoinmarketplace.presentation.testutil.TestUtil.dataStatus
import com.saldi.bitcoinmarketplace.presentation.testutil.TestUtil.initData
import com.saldi.domain.common.exception.Failure
import com.saldi.domain.common.state.DomainResult
import com.saldi.domain.features.bitcoin.entities.BitcoinValue
import com.saldi.domain.features.bitcoin.repositories.BitcoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TestDataRepository @Inject constructor() : BitcoinRepository {
    override fun fetchBitcoinInformation(
        chartName: String,
        timeSpan: String,
        rolling: String?
    ): Flow<DomainResult<BitcoinValue>> {
        return when (dataStatus) {
            DataStatus.Success -> {
                flow { emit(DomainResult.Success(initData())) }
            }
            DataStatus.Fail -> {
                flow { emit(DomainResult.Error(Failure.NetworkConnection)) }
            }
            DataStatus.EmptyResponse -> {
                flow { emit(DomainResult.Error(Failure.DataError)) }
            }
        }
    }


}