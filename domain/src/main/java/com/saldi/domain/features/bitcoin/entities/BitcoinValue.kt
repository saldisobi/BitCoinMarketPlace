package com.saldi.domain.features.bitcoin.entities


/**
 * Created by Sourabh on 10/5/21
 *
 * @property status The request's status provided by the API.
 * @property name The name of our current request.
 * @property unit The currency in which the market price is provided.
 * @property period The period between two market price entries.
 * @property description The description of our current request.
 * @property values The list of market prices over time.
 */
data class BitcoinValue(
    val status: String,
    val name: String,
    val unit: String,
    val period: String,
    val description: String,
    val values: List<BitcoinData>
)
