package com.vilinesoft.home

import com.vilinesoft.domain.model.Good

class HomeContract {
    data class UIState(
        val barcode: String = "",
    )

    sealed class UIIntent {
        data object AddClick: UIIntent()
    }

    sealed class UIEffect {
        data object CloseScreen: UIEffect()
    }
}