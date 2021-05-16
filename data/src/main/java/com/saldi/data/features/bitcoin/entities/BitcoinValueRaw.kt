package com.saldi.data.features.bitcoin.entities

import com.squareup.moshi.Json


/**
 * Created by Sourabh on 10/5/21
 *
 * @property timestamp The exact timestamp corresponding to this [BitcoinValueRaw] entry.
 * @property yAxisValue The bitcoin's value corresponding to the given [timestamp].
 */
data class BitcoinValueRaw(
    @field:Json(name = "x")
    val timestamp: Long?,

    @field:Json(name = "y")
    val yAxisValue: Float?
)