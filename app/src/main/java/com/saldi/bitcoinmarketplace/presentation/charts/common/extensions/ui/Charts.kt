package com.saldi.bitcoinmarketplace.presentation.charts.common.extensions.ui

import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.saldi.bitcoinmarketplace.R
import com.saldi.bitcoinmarketplace.presentation.charts.MainActivity
import com.saldi.bitcoinmarketplace.presentation.charts.common.constants.PresentationConstants
import com.saldi.bitcoinmarketplace.presentation.charts.common.customui.BitcoinLineChartMarkerView
import com.saldi.bitcoinmarketplace.presentation.charts.common.extensions.getFormattedDateFromTimestamp
import com.saldi.bitcoinmarketplace.presentation.charts.entities.BitcoinPresentationViewEntity


/**
 * Created by Sourabh on 12/5/21
 */

fun LineChart.configureChartEmptyView(context: Context) {
    this.setNoDataText(context.getString(R.string.bitcoin_market_price_activity_chart_placeholder_text))
    this.setNoDataTextColor(ContextCompat.getColor(context, android.R.color.black))
    this.getPaint(Chart.PAINT_INFO).textSize =
        resources.getDimensionPixelSize(R.dimen.bitcoin_activity_chart_no_data_text_size).toFloat()
}

fun LineChart.populateChart(
    context: Context,
    viewEntity: BitcoinPresentationViewEntity?
) {
    viewEntity?.let {
        // X axis
        val xAxis = this.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.setLabelCount(it.xAxisLabelCount, true)
        xAxis.setValueFormatter { value, _ ->
            value.toLong().getFormattedDateFromTimestamp(PresentationConstants.SHORT_DATE_FORMAT)
        }

        // Y axis
        this.axisLeft.granularity = 1f
        this.axisRight.isEnabled = false

        // Data set
        val dataSetElementsColor = ContextCompat.getColor(context, R.color.colorPrimary)
        val dataSet = LineDataSet(it.entryList, it.currency)
        dataSet.setDrawValues(false)
        dataSet.color = dataSetElementsColor
        dataSet.valueTextColor = dataSetElementsColor
        dataSet.highLightColor = dataSetElementsColor
        dataSet.setCircleColor(dataSetElementsColor)

        // Chart
        this.setScaleEnabled(false)
        this.description.text = it.description
        this.data = LineData(dataSet)
        this.marker = BitcoinLineChartMarkerView(it.currency, context)
        this.animateX(MainActivity.CHART_ANIMATION_DURATION_MS)
    }
}

