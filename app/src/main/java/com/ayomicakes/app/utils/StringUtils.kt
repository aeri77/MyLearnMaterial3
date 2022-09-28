package com.ayomicakes.app.utils

object StringUtils {
    fun String.getBearer() = "Bearer $this"
}