package com.ayomicakes.app.utils

object IntUtils {
    fun getDayOfMonthRange(start: Int, end: Int): List<Int> {
        val range: ArrayList<Int> = arrayListOf()
        for (dayInt in start..end){
            range.add(dayInt)
        }
        return range
    }

}