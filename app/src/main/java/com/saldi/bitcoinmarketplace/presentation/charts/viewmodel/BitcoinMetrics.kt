package com.saldi.bitcoinmarketplace.presentation.charts.viewmodel

/**
 * Created by Sourabh on 11/5/21
 */
sealed class BitcoinMetrics {
    object MarketPrice : BitcoinMetrics()
    object TradeVolume : BitcoinMetrics()
    object Transactions : BitcoinMetrics()

}