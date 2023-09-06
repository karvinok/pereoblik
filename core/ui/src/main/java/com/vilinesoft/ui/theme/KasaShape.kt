package com.vilinesoft.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object CornerRadius {
    val small = 5.dp
    val default = 7.dp
    val large = 10.dp
}

val SmallShape = RoundedCornerShape(CornerRadius.small)
val DefaultShape = RoundedCornerShape(CornerRadius.default)
val LargeShape = RoundedCornerShape(CornerRadius.large)