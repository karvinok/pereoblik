package com.vilinesoft.domain.model

import androidx.compose.runtime.Stable

@Stable
data class DocumentItem(
    val id: String,
    val idDoc: String? = null,
    val codeGoods1C: String? = null,
    val name: String? = null,
    val barcode: String? = null,
    val price: Double? = null,
    val qtyBalance: Double? = null,
    val qtyFact: Double? = null,
    val unit: String? = null,
    val isNew: Boolean = false,
)