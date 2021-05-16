package com.saldi.domain.features.bitcoin.repositories

import com.saldi.domain.common.state.DomainResult
import com.saldi.domain.features.bitcoin.entities.BitcoinValue
import kotlinx.coroutines.flow.Flow


/**
 * Created by Sourabh on 10/5/21
 *
 * This interface corresponds to the definition of the [BitcoinRepository]. Its implementation
 * can be found in the data module. It has been done this way in order to respect the Dependency
 * Inversion principle from SOLID.
 */
interface BitcoinRepository {
    /**
     * Function used to fetch the Bitcoin's market price information from the Blockchain's API.
     *
     * @param chartName Name of the chart.
     * @param timeSpan Duration of the chart.
     * @param rolling Duration over which the data should be averaged(optional).
     * @return Returns a [Flow].
     */
    fun fetchBitcoinInformation(
        chartName: String,
        timeSpan: String,
        rolling: String? = null
    ): Flow<DomainResult<BitcoinValue>>
}