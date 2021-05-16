package com.saldi.bitcoinmarketplace.presentation.charts.common.extensions

import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Created by Sourabh on 10/5/21
 *
 * Extension function used to apply a thousand separator on a [Long].
 * @param separator The separator to apply.
 * @return Returns the formatted number.
 */
fun Long.applyThousandsSeparator(separator: Char): String {

    val formatter = NumberFormat.getInstance() as DecimalFormat
    val symbols = formatter.decimalFormatSymbols.apply {
        groupingSeparator = separator
    }
    formatter.decimalFormatSymbols = symbols
    return formatter.format(this)
}
