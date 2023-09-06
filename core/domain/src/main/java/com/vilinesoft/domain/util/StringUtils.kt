package com.vilinesoft.domain.util

fun Double.toDoubleString(): String {
    return String.format("%.2f", this)
}