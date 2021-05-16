package com.saldi.data.features.bitcoin.network

import com.saldi.data.common.constants.DataConstants.BLOCKCHAIN_BASE_URL
import com.saldi.data.common.network.interceptors.NetworkCacheInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object BitcoinApiServiceFactory {
    fun createApiService(
        isDebug: Boolean,
        cache: Cache,
        connectTimeout: Long,
        readTimeout: Long
    ): BitcoinService {
        val okHttpClient: OkHttpClient = makeOkHttpClient(
            makeLoggingInterceptor((isDebug)), cache, connectTimeout, readTimeout
        )
        return makeApiService(okHttpClient)
    }

    private fun makeApiService(okHttpClient: OkHttpClient): BitcoinService {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BLOCKCHAIN_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
        return retrofit.create(BitcoinService::class.java)
    }

    private fun makeOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache,
        connectTimeout: Long,
        readTimeout: Long
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(NetworkCacheInterceptor())
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}
