package com.saldi.data.features.bitcoin.remote

import com.saldi.data.features.bitcoin.mappers.BitcoinDataMapper
import com.saldi.data.features.bitcoin.network.BitcoinService
import com.saldi.domain.common.exception.Failure
import com.saldi.domain.common.state.DomainResult
import com.saldi.domain.features.bitcoin.entities.BitcoinValue
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by Sourabh on 10/5/21
 */
class BitcoinSourceRemoteImpl @Inject constructor(
    private val apiService: BitcoinService,
    private val bitcoinDataMapper: BitcoinDataMapper
) : BitcoinSourceRemote {
    override suspend fun fetchBitcoinInformation(
        chartName: String,
        timeSpan: String,
        rollingAverage: String?
    ): DomainResult<BitcoinValue> {
        return try {
            val res = apiService.fetchBitcoinInfo(chartName, timeSpan, rollingAverage)
            when (res.isSuccessful) {
                true -> {
                    res.body()?.let {
                        DomainResult.Success(bitcoinDataMapper.invoke(it))
                    } ?: DomainResult.Error(Failure.DataError)
                }
                false -> {
                    DomainResult.Error(Failure.ServerError)
                }
            }
        } catch (e: UnknownHostException) {
            DomainResult.Error(Failure.NetworkConnection)

        } catch (e: SocketTimeoutException) {
            DomainResult.Error(Failure.NetworkConnection)

        } catch (e: Exception) {
            DomainResult.Error(Failure.ServerError)
        }
    }
}