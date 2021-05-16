package com.saldi.bitcoinmarketplace.presentation.charts.common.customui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.view.LayoutInflater
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.saldi.bitcoinmarketplace.R
import com.saldi.bitcoinmarketplace.databinding.BitcoinMarketPriceLinechartMarkerviewBinding
import com.saldi.bitcoinmarketplace.presentation.charts.common.constants.PresentationConstants.LONG_DATE_FORMAT
import com.saldi.bitcoinmarketplace.presentation.charts.common.constants.PresentationConstants.THOUSANDS_SEPARATOR
import com.saldi.bitcoinmarketplace.presentation.charts.common.extensions.applyThousandsSeparator
import com.saldi.bitcoinmarketplace.presentation.charts.common.extensions.getFormattedDateFromTimestamp


/**
 * Created by Sourabh on 10/5/21
 */
@SuppressLint("ViewConstructor")
class BitcoinLineChartMarkerView(private val currency: String, context: Context) :
    MarkerView(
        context,
        R.layout.bitcoin_market_price_linechart_markerview
    ) {

    private var binding: BitcoinMarketPriceLinechartMarkerviewBinding =
        BitcoinMarketPriceLinechartMarkerviewBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    /**
     * Width of the device's screen.
     */
    private val screenWidthInPx = resources.displayMetrics.widthPixels

    /**
     * Offset used to position the [MarkerView] on the graph.
     */
    private var customOffset: MPPointF? = null

    override fun refreshContent(entry: Entry, highlight: Highlight) {

        val formattedDate = entry.x.toLong().getFormattedDateFromTimestamp(LONG_DATE_FORMAT)
        val formattedAmount = entry.y.toLong().applyThousandsSeparator(THOUSANDS_SEPARATOR)
        val amountWithCurrency = "$currency: $formattedAmount"

        binding.topTextView.text = formattedDate
        binding.bottomTextView.text = amountWithCurrency

        super.refreshContent(entry, highlight)
    }

    override fun getOffset(): MPPointF {

        if (customOffset == null) {
            customOffset = MPPointF(-(width / 2).toFloat(), -height.toFloat())
        }

        return customOffset as MPPointF
    }

    override fun draw(canvas: Canvas, posX: Float, posY: Float) {

        var newPosX = posX

        val width = width
        if (screenWidthInPx - newPosX - width < width) {
            newPosX -= width.toFloat()
        }

        canvas.translate(newPosX, posY)
        draw(canvas)
        canvas.translate(-newPosX, -posY)
    }
}
