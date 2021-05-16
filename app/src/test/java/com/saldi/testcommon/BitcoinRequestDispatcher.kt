package com.saldi.testcommon

import com.saldi.testcommon.UrlConstants.MARKET_PRICE_REQUEST
import com.saldi.testcommon.UrlConstants.UNKNOWN_REQUEST
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

class BitcoinRequestDispatcher {

    /**
     * Return ok response from mock server
     */
    inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                MARKET_PRICE_REQUEST -> {
                    MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(getJson("response/market_price.json"))
                }
                UNKNOWN_REQUEST -> {
                    MockResponse()
                        .setResponseCode(HttpURLConnection.HTTP_OK)
                        .setBody(getJson("response/unknown_chart.json"))
                }
                else -> throw IllegalArgumentException("Unknown Request Path ${request.path}")
            }
        }
    }

    /**
     * Return bad request response from mock server
     */
    internal inner class BadRequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest) =
            MockResponse().setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
    }

    /**
     * Return server error response from mock server
     */
    internal inner class ErrorRequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest) =
            MockResponse().setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
    }
}