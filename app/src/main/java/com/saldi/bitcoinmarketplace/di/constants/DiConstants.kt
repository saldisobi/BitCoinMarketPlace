package com.saldi.bitcoinmarketplace.di.constants


/**
 * Created by Sourabh on 15/5/21
 */
object DiConstants {
    const val CONNECTION_TIMEOUT_NAME = "connectionTimeout"
    const val READ_TIMEOUT_NAME = "readTimeout"
    const val CONNECTION_TIMEOUT_VALUE = 30L
    const val READ_TIMEOUT_VALUE = 30L
    const val CACHE_SIZE = 5 * 1024 * 1024.toLong()
}