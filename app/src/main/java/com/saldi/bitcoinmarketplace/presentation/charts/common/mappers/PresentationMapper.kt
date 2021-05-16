package com.saldi.bitcoinmarketplace.presentation.charts.common.mappers

/**
 * Created by Sourabh on 10/5/21
 */
interface PresentationMapper<in I, out O> {
    operator fun invoke(input: I): O
}