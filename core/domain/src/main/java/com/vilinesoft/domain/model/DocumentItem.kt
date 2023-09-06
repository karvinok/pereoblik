package com.vilinesoft.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentItem(
    var id: String,
    var idDoc: String? = null,
    var codeGoods1C: String? = null,
    var name: String? = null,
    var barcode: String? = null,
    var price: Double? = null,
    var qtyBalance: Double? = null,
    var qtyFact: Double? = null,
    var unit: String? = null,
    var isNew: Boolean = false,
): Parcelable