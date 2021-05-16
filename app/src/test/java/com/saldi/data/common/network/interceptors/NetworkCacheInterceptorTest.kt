package com.saldi.data.common.network.interceptors

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Test


/**
 * Created by Sourabh on 13/5/21
 */
class NetworkCacheInterceptorTest {


    @Test
    @Throws(Exception::class)
    fun testHttpInterceptor() {
        val networkCacheInterceptor = NetworkCacheInterceptor()
        val mockWebServer = MockWebServer()
        mockWebServer.start()
        mockWebServer.enqueue(MockResponse())
        val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(networkCacheInterceptor).build()
        okHttpClient.newCall(Request.Builder().url(mockWebServer.url("/")).build()).execute()
        Assert.assertEquals(
            "max-age=300", networkCacheInterceptor.cacheControl.toString()
        )
        mockWebServer.shutdown()
    }
}


