package com.saldi.bitcoinmarketplace.presentation.charts.mapper

import com.github.mikephil.charting.data.Entry
import com.saldi.bitcoinmarketplace.presentation.charts.common.mappers.PresentationMapper
import com.saldi.bitcoinmarketplace.presentation.charts.entities.BitcoinPresentationViewEntity
import com.saldi.domain.features.bitcoin.entities.BitcoinValue
import javax.inject.Inject


/**
 * Created by Sourabh on 10/5/21
 */
class PresentationMapperImpl @Inject constructor() :
    PresentationMapper<BitcoinValue, BitcoinPresentationViewEntity> {

    override fun invoke(input: BitcoinValue): BitcoinPresentationViewEntity {

        val entryList = input.values.map { Entry(it.timestamp.toFloat(), it.amount) }
        val xAxisLabelCount = when (entryList.size) {
            0 -> 0  // Will not occur, since already checked.
            1 -> 1
            2 -> 2
            3 -> 3
            else -> 4
        }

        return BitcoinPresentationViewEntity(
            entryList = entryList,
            xAxisLabelCount = xAxisLabelCount,
            currency = input.unit,
            description = input.description
        )
    }
}
