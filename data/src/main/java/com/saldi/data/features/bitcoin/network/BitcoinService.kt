package com.saldi.data.features.bitcoin.network

import com.saldi.data.features.bitcoin.entities.BitcoinDataRaw
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created by Sourabh on 10/5/21
 */
interface BitcoinService {
    @GET("charts/{chartName}")
    suspend fun fetchBitcoinInfo(
        @Path("chartName") chartName: String,
        @Query("timespan") timeSpan: String,
        @Query("rollingAverage") rollingAverage: String? = null
    ): Response<BitcoinDataRaw>
}