package com.ayomicakes.app.model

data class SelectableData<T>(
    val isSelected: Boolean,
    val value: T
)
