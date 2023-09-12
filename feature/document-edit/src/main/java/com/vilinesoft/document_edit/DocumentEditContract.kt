package com.vilinesoft.document_edit

import com.vilinesoft.domain.model.Document

class DocumentEditContract {

    data class UIState(
        val document: Document? = null,
    )

    sealed class UIIntent {
    }

    sealed class UIEffect {
        data object CloseScreen: UIEffect()
    }
}