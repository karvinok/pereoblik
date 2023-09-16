package com.vilinesoft.domain.model

import androidx.compose.runtime.Stable

@Stable
data class Good(
    val id: Int = 0,
    val goodName: String? = null,
    val goodPrice: Double? = null,
    val barcode: String? = null,
    val goodCode: String? = null,
    val goodCount: Double? = null,
    val unit: String? = null,
    val isWeight: Int = 0,
    val tableRowCount: Int = 0,
    val currentRow: Int = 0
)