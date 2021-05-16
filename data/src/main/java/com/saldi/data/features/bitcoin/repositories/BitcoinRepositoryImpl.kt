package com.saldi.data.features.bitcoin.repositories

import com.saldi.data.features.bitcoin.remote.BitcoinSourceRemote
import com.saldi.domain.features.bitcoin.entities.BitcoinValue
import com.saldi.domain.features.bitcoin.repositories.BitcoinRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.saldi.domain.common.state.DomainResult
import kotlinx.coroutines.flow.flow


/**
 * Created by Sourabh on 10/5/21
 *
 * This class corresponds to the implementation of the [BitcoinRepository]. Its definition can be
 * found in the domain module. It has been done this way in order to respect the Dependency
 * Inversion principle from SOLID.
 */

class BitcoinRepositoryImpl @Inject constructor(
    private val bitcoinSourceRemote: BitcoinSourceRemote
) : BitcoinRepository {
    override fun fetchBitcoinInformation(
        chartName: String,
        timeSpan: String,
        rolling: String?
    ): Flow<DomainResult<BitcoinValue>> {
        return flow {
            when (val bitcoinPriceInformationResponse =
                bitcoinSourceRemote.fetchBitcoinInformation(chartName, timeSpan, rolling)) {
                is DomainResult.Success -> {
                    emit(bitcoinPriceInformationResponse)
                }
                is DomainResult.Error -> {
                    emit(DomainResult.Error(bitcoinPriceInformationResponse.failure))
                }
            }
        }
    }
}
