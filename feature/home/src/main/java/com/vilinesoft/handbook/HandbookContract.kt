package com.vilinesoft.handbook

import com.vilinesoft.domain.model.Good

class HandbookContract {

    data class UIState(
        val good: Good? = null,
        val loading: Boolean = false,
        val notFound: Boolean = false,
        val barcode: String = "",
    )

    sealed class UIIntent {
        data object AddClick: UIIntent()
    }

    sealed class UIEffect {
        data object ShowToast: UIEffect()
    }
}