package com.vilinesoft.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.vilinesoft.data.BaseResponse
import com.vilinesoft.domain.model.Good
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
class GoodsDto(
    val data: Good
) : BaseResponse(), Parcelable