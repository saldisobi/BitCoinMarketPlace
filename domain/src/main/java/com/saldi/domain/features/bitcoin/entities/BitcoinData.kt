package com.saldi.domain.features.bitcoin.entities


/**
 * Created by Sourabh on 10/5/21
 *
 * @property timestamp The exact timestamp corresponding to this [BitcoinData] entry.
 * @property amount The bitcoin's value corresponding to the given [timestamp].
 */
data class BitcoinData(
    val timestamp: Long,
    val amount: Float
)
