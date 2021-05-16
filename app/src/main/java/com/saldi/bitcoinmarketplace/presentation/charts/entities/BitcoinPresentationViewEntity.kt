package com.saldi.bitcoinmarketplace.presentation.charts.entities

import com.github.mikephil.charting.data.Entry


/**
 * Created by Sourabh on 10/5/21
 *
 * @property entryList The list of entries to be displayed in the graph.
 * @property xAxisLabelCount The quantity of labels to display on the X axis.
 * @property currency The bitcoin price's currency.
 * @property description The description of the graph.
 */
class BitcoinPresentationViewEntity(
    val entryList: List<Entry>,
    val xAxisLabelCount: Int,
    val currency: String,
    val description: String
)