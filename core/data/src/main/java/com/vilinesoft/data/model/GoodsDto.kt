package com.vilinesoft.data.model

import androidx.annotation.Keep
import com.vilinesoft.data.BaseResponse
import com.vilinesoft.domain.model.Good

@Keep
class GoodsDto(
    val data: Good
) : BaseResponse()