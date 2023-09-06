package com.vilinesoft.data.model

import androidx.annotation.Keep
import com.vilinesoft.data.BaseResponse
import org.w3c.dom.DocumentType

@Keep
class DocumentTypesDto(
    val data: List<DocumentType>
): BaseResponse()
