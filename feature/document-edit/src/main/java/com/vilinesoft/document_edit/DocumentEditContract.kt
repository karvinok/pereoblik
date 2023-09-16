package com.vilinesoft.document_edit

import com.vilinesoft.domain.model.Document

class DocumentEditContract {

    data class UIState(
        val document: Document? = null,
        val barcode: String = "",
        val error: Boolean = false,
        val dialogItemCreationVisible: Boolean = false
    )

    sealed class UIIntent {
        data object ClickItem: UIIntent()
        data object LongClickItem: UIIntent()
        data object DismissCreationDialog: UIIntent()
        data class CreateItemConfirmClick(val name: String?, val price: String?, val qty: String?): UIIntent()
    }

    sealed class UIEffect {
        data object CloseScreen: UIEffect()
    }
}