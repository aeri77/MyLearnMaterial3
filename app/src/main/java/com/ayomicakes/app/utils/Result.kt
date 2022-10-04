package com.ayomicakes.app.utils

sealed class Result<out R> private constructor() {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: String, val errCode: Int? = null ) : Result<Nothing>()
    object Loading : Result<Nothing>()
}