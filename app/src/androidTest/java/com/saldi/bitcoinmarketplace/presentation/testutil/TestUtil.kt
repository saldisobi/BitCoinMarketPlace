package com.saldi.bitcoinmarketplace.presentation.testutil

import com.saldi.domain.features.bitcoin.entities.BitcoinData
import com.saldi.domain.features.bitcoin.entities.BitcoinValue

object TestUtil {
    var dataStatus: DataStatus = DataStatus.Success

    fun initData(): BitcoinValue {
        return BitcoinValue(
            status = "ok",
            name = "Confirmed Transactions Per Day",
            unit = "Transactions",
            period = "day",
            description = "The number of daily confirmed Bitcoin transactions.",
            values = listOf(BitcoinData(1L, 1f))

        )
    }
}