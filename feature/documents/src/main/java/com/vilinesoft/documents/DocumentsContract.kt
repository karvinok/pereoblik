package com.vilinesoft.documents

import com.vilinesoft.domain.model.Document
import com.vilinesoft.domain.model.DocumentType

class DocumentsContract {

    data class UIState(
        val isActionMode: Boolean = false,
        val documents: List<Document> = listOf(),
        val dialogCreateDocState: CreateDocumentDialogState? = null,
        val isDeleteAlertDialogVisible: Boolean = false,
    )

    sealed class UIIntent {
        data object AddClick: UIIntent()
        data object SendClick: UIIntent()
        data object AskDeleteClick: UIIntent()
        data object AlertDismissClick: UIIntent()
        data object AlertDeleteClick: UIIntent()
        data class ItemClick(val index: Int): UIIntent()
        data class ItemLongClick(val index: Int): UIIntent()
        data class DocumentTypeSelect(val type: DocumentType): UIIntent()
        data class CommentChange(val comment: String): UIIntent()
        data object DialogDismiss: UIIntent()
        data object DocumentCreateClick: UIIntent()
    }

    sealed class UIEffect {
        data class NavigateDocument(val documentId: String): UIEffect()
        data class ShowToast(val text: String): UIEffect()
    }

    data class CreateDocumentDialogState(
        val comment: String = "",
        val documentTypes: List<DocumentType> = listOf(),
        val selectedDocType: DocumentType = DocumentType.PEREOB
    )
}