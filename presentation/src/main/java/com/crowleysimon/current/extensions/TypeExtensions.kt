package com.crowleysimon.current.extensions

import android.content.Context
import com.crowleysimon.current.R
import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.format.DateTimeFormat
import java.util.*

/**
 * Now and less than 1 day old - 9:52 am
 * 1 day old - Yesterday
 * 2 to 6 days old - Sat
 * Older than 7 days - 3 Jun
 * Older than 1 year - 26 May 2017
 */
fun DateTime.formatTimeStamp(context: Context): String {
    val now = DateTime()

    // For times that take place on the same day (e.g. 9:41 am)
    if (this.dayOfYear == now.dayOfYear && this.year == now.year) {
        val dtf = DateTimeFormat.forPattern("h:mm a")
        return dtf.print(this).replace("AM", "am").replace("PM", "pm")
    }

    // For times that take place the day before but only if the phone is set to english
    if (Locale.getDefault().isO3Language == "eng" &&
        this.dayOfYear == now.dayOfYear - 1 &&
        this.year == now.year
    ) {
        return context.getString(R.string.yesterday)
    }

    // For times that take place in the past week (e.g. Wed)
    val oneWeek = Interval(now.minusWeeks(1), now)
    if (oneWeek.contains(this)) {
        val dtf = DateTimeFormat.forPattern("EEE")
        return dtf.print(this)
    }

    // For times that are over one week old but less than one year (e.g. 13 May)
    val oneYear = Interval(now.minusYears(1), now)
    if (oneYear.contains(this)) {
        val dtf = DateTimeFormat.forPattern("dd MMMM")
        return dtf.print(this)
    }

    // Finally for times over a year ago (e.g. 13 May 2015)
    val dtf = DateTimeFormat.forPattern("dd MMMM yyyy")
    return dtf.print(this)
}