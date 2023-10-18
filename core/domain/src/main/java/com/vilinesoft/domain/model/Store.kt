package com.vilinesoft.domain.model

import androidx.compose.runtime.Stable

@Stable
data class Store(
    val code: String,
    val name: String
){
    override fun toString(): String {
        return name
    }
}
