package com.vilinesoft.domain.model

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class Document(
    val id: String = "",
    val numberDoc: String? = "",
    val dateDoc: String? = null,
    val commDoc: String? = null,
    val docType: DocumentType? = null,
    val is1C: Int? = null,
    val isChanged: Int? = null,
    val storeCode: String? = null,
    val items: ImmutableList<DocumentItem> = persistentListOf(),
    val isSelected: Boolean = false
)