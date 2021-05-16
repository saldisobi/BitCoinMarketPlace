package com.saldi.data.common.network.interceptors

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

/**
 * Created by Sourabh on 13/5/21
 *
 * This class is responsible for configuration of caching from retrofit
 */
open class NetworkCacheInterceptor : Interceptor {
    val cacheControl = CacheControl.Builder()
        .maxAge(5, TimeUnit.MINUTES)
        .build()

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())

        return response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }

    companion object {
        const val HEADER_CACHE_CONTROL = "Cache-Control"
        const val HEADER_PRAGMA = "Pragma"
    }
}