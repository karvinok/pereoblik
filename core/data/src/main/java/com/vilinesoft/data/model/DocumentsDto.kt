package com.vilinesoft.data.model

import androidx.annotation.Keep

@Keep
class DocumentsDto(
    val docs: List<DocumentEntity>,
    val items: List<DocumentItemEntity>,
)