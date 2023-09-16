package com.vilinesoft.documents

import androidx.lifecycle.viewModelScope
import com.vilinesoft.documents.DocumentsContract.*
import com.vilinesoft.domain.model.Document
import com.vilinesoft.domain.model.DocumentType
import com.vilinesoft.domain.repository.MainRepository
import com.vilinesoft.ui.BaseViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class DocumentsViewModel(
    private val repository: MainRepository
) : BaseViewModel<UIIntent, UIState, UIEffect>() {

    override fun provideDefaultState() = UIState()

    private var documentTypesLocal: List<DocumentType> = arrayListOf()

    init {
        requestDocumentTypes()
        updateState {
            copy(documents = repository.fetchDocuments())
        }
    }

    override fun handleIntent(intent: UIIntent) {
        when(intent) {
            UIIntent.AddClick -> onAddClick()
            UIIntent.SendClick -> sendDocuments()
            UIIntent.AskDeleteClick -> if (uiState.value.isActionMode) updateState {
                copy(isDeleteAlertDialogVisible = true)
            }
            UIIntent.AlertDismissClick -> updateState { copy(isDeleteAlertDialogVisible = false) }
            UIIntent.AlertDeleteClick -> deleteSelectedDocuments()
            is UIIntent.ItemClick -> onItemClick(intent.index)
            is UIIntent.ItemLongClick -> onItemLongClick(intent.index)
            is UIIntent.CommentChange -> updateState {
                copy(dialogCreateDocState = dialogCreateDocState?.copy(comment = intent.comment))
            }
            is UIIntent.DocumentTypeSelect -> updateState {
                copy(dialogCreateDocState = dialogCreateDocState?.copy(selectedDocType = intent.type))
            }
            UIIntent.DialogDismiss -> updateState { copy(dialogCreateDocState = null) }
            UIIntent.DocumentCreateClick -> createDocumentAndSave()
        }
    }

    private fun onAddClick() {
        updateState {
            if (isActionMode) {
                val fetchedDocuments = repository.fetchDocuments()
                copy(isActionMode = false, documents = fetchedDocuments.toImmutableList())
            } else {
                if (documentTypesLocal.isEmpty()) {
                    requestDocumentTypes()
                    return@updateState copy()
                }

                copy(dialogCreateDocState = CreateDocumentDialogState(
                    documentTypes = documentTypesLocal.toImmutableList()
                ))
            }
        }
    }

    private fun onItemClick(index: Int) {
        updateState {
            val mutableDocuments = documents.toMutableList()
            if (isActionMode) {
                mutableDocuments[index] = mutableDocuments[index].copy(
                    isSelected = !mutableDocuments[index].isSelected
                )
                copy(
                    isActionMode = mutableDocuments.any { it.isSelected },
                    documents = mutableDocuments.toImmutableList()
                )
            } else {
                postEffect(UIEffect.NavigateDocument(mutableDocuments[index].id))
                this
            }
        }
    }

    private fun onItemLongClick(index: Int) {
        updateState {
            if (documents[index].isSelected) return@updateState this

            val mutableDocuments = documents.toMutableList()
            mutableDocuments[index] = mutableDocuments[index].copy(isSelected = true)
            return@updateState copy(
                documents = mutableDocuments.toImmutableList(),
                isActionMode = true
            )
        }
    }

    private fun sendDocuments() {
        if (!uiState.value.isActionMode) return

        postEffect(UIEffect.ShowToast("Send"))
    }

    private fun deleteSelectedDocuments() {
        updateState {
            val ids = documents.filter { it.isSelected }.map { it.id }
            repository.deleteDocuments(ids)
            copy(
                isActionMode = false,
                documents = repository.fetchDocuments(),
                isDeleteAlertDialogVisible = false
            )
        }
    }

    private fun createDocumentAndSave() {
        val dateTime = SimpleDateFormat("dd.MM.yyyy, HH:mm",
            Locale.getDefault()).format(Calendar.getInstance().time)

        updateState {
            val doc = Document(
                id = UUID.randomUUID().toString(),
                numberDoc = repository.getDocumentsCount().inc().toString(),
                dateDoc = dateTime,
                commDoc = dialogCreateDocState?.comment?: "",
                docType = DocumentType.valueOf(dialogCreateDocState?.selectedDocType?.typeNumber),
                is1C = 0,
                isChanged = 1
            )
            repository.saveDocument(doc)
            copy(
                documents = repository.fetchDocuments(), //TODO replace by flow
                dialogCreateDocState = null
            )
        }
    }

    private fun requestDocumentTypes() {
        viewModelScope.launch(Dispatchers.IO) {
            documentTypesLocal = repository.requestDocumentTypes()
        }
    }
}