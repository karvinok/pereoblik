package com.vilinesoft.data

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
open class BaseResponse(
    var status: Int = 0,
    var message: String? = null
) : Parcelable