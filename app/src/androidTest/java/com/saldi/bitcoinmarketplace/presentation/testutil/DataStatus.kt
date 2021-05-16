package com.saldi.bitcoinmarketplace.presentation.testutil

sealed class DataStatus {
    object Success : DataStatus()
    object Fail : DataStatus()
    object EmptyResponse : DataStatus()
}