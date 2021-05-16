package com.saldi.bitcoinmarketplace.presentation.charts.common.extensions.ui

import com.saldi.bitcoinmarketplace.R
import com.saldi.bitcoinmarketplace.presentation.charts.common.constants.PresentationConstants.MARKET_PRICE
import com.saldi.bitcoinmarketplace.presentation.charts.common.constants.PresentationConstants.TRADE_VOLUME
import com.saldi.bitcoinmarketplace.presentation.charts.common.constants.PresentationConstants.TRANSACTIONS
import com.saldi.bitcoinmarketplace.presentation.charts.viewmodel.BitcoinMetrics


/**
 * Created by Sourabh on 13/5/21
 */
enum class BottomNavigationPosition(val tag: BitcoinMetrics, val id: Int) {
    MARKET(BitcoinMetrics.MarketPrice, R.id.bottom_navigation_bar_money_icon),
    TRANSACTIONS(BitcoinMetrics.TradeVolume, R.id.bottom_navigation_bar_transactions_icon),
    TIME(BitcoinMetrics.Transactions, R.id.bottom_navigation_bar_trade_volume_icon);
}

fun findNavigationPositionById(id: Int): BottomNavigationPosition = when (id) {
    BottomNavigationPosition.MARKET.id -> BottomNavigationPosition.MARKET
    BottomNavigationPosition.TRANSACTIONS.id -> BottomNavigationPosition.TRANSACTIONS
    BottomNavigationPosition.TIME.id -> BottomNavigationPosition.TIME
    else -> BottomNavigationPosition.MARKET
}

fun findNavigationText(id: Int): String = when (id) {
    BottomNavigationPosition.MARKET.id -> MARKET_PRICE
    BottomNavigationPosition.TRANSACTIONS.id -> TRANSACTIONS
    BottomNavigationPosition.TIME.id -> TRADE_VOLUME
    else -> MARKET_PRICE
}