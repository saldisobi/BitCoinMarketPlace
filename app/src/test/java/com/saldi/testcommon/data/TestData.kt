package com.saldi.testcommon.data

import com.saldi.domain.features.bitcoin.entities.BitcoinData
import com.saldi.domain.features.bitcoin.entities.BitcoinValue


/**
 * Created by Sourabh on 13/5/21
 */
object TestData {
    val transactionData = BitcoinValue(
        status = "ok",
        name = "Confirmed Transactions Per Day",
        unit = "Transactions",
        period = "day",
        description = "The number of daily confirmed Bitcoin transactions.",
        values = listOf(BitcoinData(1L, 1f))
    )

    val tradeVolumeData = BitcoinValue(
        status = "ok",
        name = "USD Exchange Trade Volume",
        unit = "Trade Volume (USD)",
        period = "day",
        description = "The total USD value of trading volume on major bitcoin exchanges.",
        values = listOf(BitcoinData(1L, 1f))
    )
    val marketPriceData = BitcoinValue(
        status = "ok",
        name = "Market Price (USD)",
        unit = "USD",
        period = "day",
        description = "Average USD market price across major bitcoin exchanges.",
        values = listOf(BitcoinData(1L, 1f))
    )
}