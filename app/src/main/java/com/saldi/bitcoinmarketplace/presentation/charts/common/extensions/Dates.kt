package com.saldi.bitcoinmarketplace.presentation.charts.common.extensions

import android.annotation.SuppressLint
import java.sql.Date
import java.sql.Timestamp
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Sourabh on 10/5/21
 *
 * Extension function used to get a formatted date from a timestamp, depending on the date
 * pattern passed in as a parameter.
 *
 * @param datePattern The date pattern to apply to the timestamp.
 * @return Returns the formatted date in case it goes well. Otherwise, returns a default
 * formatted date.
 */
@SuppressLint("SimpleDateFormat")
fun Long.getFormattedDateFromTimestamp(datePattern: String): String {

    val timestamp = Timestamp(this * 1000)
    val date = Date(timestamp.time)
    val englishLocale = Locale("en", "us")

    return try {
        val dateFormat = SimpleDateFormat(datePattern, englishLocale)
        dateFormat.format(date)
    } catch (e: Exception) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val month = calendar.get(Calendar.MONTH)
        val monthName = DateFormatSymbols(englishLocale).shortMonths[month]
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        "$dayOfMonth. $monthName" // Default format, in case there is an issue applying the pattern.
    }
}
