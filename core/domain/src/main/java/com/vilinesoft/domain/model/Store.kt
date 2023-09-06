package com.vilinesoft.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Store(
    val code: String,
    val name: String
): Parcelable