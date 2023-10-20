package com.vilinesoft.document_edit

import com.vilinesoft.domain.model.Document

class DocumentEditContract {

    data class UIState(
        val document: Document? = null,
        val barcode: String = "",
        val error: Boolean = false,
        val dialogItemCreationVisible: Boolean = false,
        val dialogUpdateItemState: UpdateItemDialogState? = null
    )

    data class UpdateItemDialogState(
        val itemName: String,
        val isNameEditable: Boolean,
        val qtyFact: Double?,
        val qtyBalance: Double?,
        val count: Double?,
        val price: Double?,
        val unitType: String?
    )

    sealed interface UIIntent {
        data class ItemClick(val itemIndex: Int): UIIntent
        data class ItemLongClick(val itemIndex: Int): UIIntent
        data object DialogCreationDismiss: UIIntent
        data class CreateItemConfirmClick(val name: String?, val price: String?, val qty: String?): UIIntent
    }

    sealed interface UpdateItemDialogIntent: UIIntent {
        data class NameChanged(val name: String): UpdateItemDialogIntent
        data class CountChanged(val count: String): UpdateItemDialogIntent
        data object DialogConfirmClick: UpdateItemDialogIntent
        data object DialogDismiss: UpdateItemDialogIntent
    }

    sealed class UIEffect {
        data object CloseScreen: UIEffect()
    }
}