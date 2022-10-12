package com.ayomicakes.app.database.model

data class CartItem<T>(
    var count: Int,
    val item: T
)
