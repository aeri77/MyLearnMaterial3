package com.ayomicakes.app.utils

import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

object IntUtils {
    fun getDayOfMonthRange(start: Int, end: Int): List<Int> {
        val range: ArrayList<Int> = arrayListOf()
        for (dayInt in start..end){
            range.add(dayInt)
        }
        return range
    }

    fun Int.toCurrency(locale: String): String? {
        val format: NumberFormat = NumberFormat.getCurrencyInstance()
        format.maximumFractionDigits = 0
        format.currency = Currency.getInstance(locale)
        return(format.format(this))
    }
}