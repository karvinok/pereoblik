package com.vilinesoft.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Document(
    val id: String = "",
    val numberDoc: String? = "",
    val dateDoc: String? = null,
    val commDoc: String? = null,
    val docType: DocumentType? = null,
    val is1C: Int? = null,
    val isChanged: Int? = null,
    val storeCode: String? = null,
    val items: List<DocumentItem>? = null,
    var isSelected: Boolean = false
): Parcelable