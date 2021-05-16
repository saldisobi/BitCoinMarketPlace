package com.saldi.domain.common.constants


/**
 * Created by Sourabh on 13/5/21
 *
 * Constants used by various use cases
 */
object DomainConstants {
    /**
     * Params for Market Price
     */
    const val CHART_NAME_MARKET_PRICE_PATH = "market-price"
    const val TIME_SPAN_MARKET_PRICE = "30days"

    /**
     * Params for trade Volume
     */
    const val CHART_NAME_TRADE_VOLUME_PATH = "trade-volume"
    const val TIME_SPAN_TRADE_VOLUME = "30days"
    const val ROLLING_AVERAGE_TRADE_VOLUME = "8hours"

    /**
     * Params for transactions
     */
    const val CHART_NAME_N_TRANSACTIONS_PATH = "n-transactions"
    const val TIME_SPAN_TRANSACTIONS = "30days"
}