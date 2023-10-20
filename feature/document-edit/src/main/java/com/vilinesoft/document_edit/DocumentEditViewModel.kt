package com.vilinesoft.document_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vilinesoft.document_edit.DocumentEditContract.UIEffect
import com.vilinesoft.document_edit.DocumentEditContract.UIIntent
import com.vilinesoft.document_edit.DocumentEditContract.UIState
import com.vilinesoft.document_edit.DocumentEditContract.UpdateItemDialogIntent
import com.vilinesoft.document_edit.DocumentEditContract.UpdateItemDialogState
import com.vilinesoft.domain.model.DocumentItem
import com.vilinesoft.domain.repository.MainRepository
import com.vilinesoft.ui.BaseViewModel
import com.vilinesoft.ui.keyprocessing.ArrowEvent
import com.vilinesoft.ui.keyprocessing.KeyEventBus
import com.vilinesoft.ui.keyprocessing.KeyEventHandler
import com.vilinesoft.ui.keyprocessing.TechKeyEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.util.UUID

class DocumentEditViewModel(
    savedStateHandle: SavedStateHandle,
    override val keyEventBus: KeyEventBus,
    private val repository: MainRepository
) : BaseViewModel<UIIntent, UIState, UIEffect>(), KeyEventHandler {

    override var keyEventsJob: Job? = Job()
    override var scope: CoroutineScope = viewModelScope
    override fun provideDefaultState() = UIState()

    private val documentId: String = checkNotNull(savedStateHandle["document_id"])

    init {
        updateState {
            copy(document = repository.fetchDocumentById(documentId))
        }
    }

    override fun handleIntent(intent: UIIntent) {
        when (intent) {
            is UIIntent.DialogCreationDismiss -> updateState {
                copy(dialogItemCreationVisible = false, barcode = "")
            }

            is UIIntent.CreateItemConfirmClick -> handleCreateItemConfirmClick(intent)
            is UIIntent.ItemLongClick -> updateState {
                val item = document?.items?.getOrNull(intent.itemIndex)
                    ?: return@updateState copy()

                val state = UpdateItemDialogState(
                    itemName = item.name ?: "",
                    isNameEditable = false,
                    qtyFact = item.qtyFact,
                    qtyBalance = item.qtyBalance,
                    count = item.qtyFact,
                    price = item.price,
                    unitType = item.unit,
                )
                copy(dialogUpdateItemState = state, barcode = "")
            }

            is UpdateItemDialogIntent.NameChanged -> updateState {
                if (true) {
                    copy(
                        dialogUpdateItemState = dialogUpdateItemState?.copy(itemName = intent.name)
                    )
                } else copy()
            }

            is UpdateItemDialogIntent.DialogConfirmClick -> updateState {
                copy(dialogUpdateItemState = null)
            }

            is UpdateItemDialogIntent.DialogDismiss -> updateState {
                copy(dialogUpdateItemState = null)
            }

            else -> Unit
        }
    }

    private fun handleCreateItemConfirmClick(intent: UIIntent.CreateItemConfirmClick) {
        if (intent.name.isNullOrBlank()
            || intent.price.isNullOrBlank()
            || intent.qty.isNullOrBlank()
        ) return

        updateState {
            val item = DocumentItem(
                id = UUID.randomUUID().toString(),
                idDoc = documentId,
                barcode = barcode,
                price = intent.price.toDouble(),
                name = intent.name.trim(),
                unit = "шт",
                isNew = true,
                codeGoods1C = "",
                qtyBalance = 0.0,
                qtyFact = intent.qty.toDouble(),
            )
            repository.saveItem(item)

            val document = repository.fetchDocumentById(documentId)
            copy(
                dialogItemCreationVisible = false,
                barcode = "",
                document = document
            )
        }
    }

    override fun onArrowEvent(event: ArrowEvent) {}

    override fun onNumberEvent(number: Int) {
        updateState {
            copy(
                barcode = barcode + number.toString(),
                error = false
            )
        }
    }

    override fun onTechKeyEvent(event: TechKeyEvent) {
        when (event) {
            TechKeyEvent.BACKPRESS -> postEffect(UIEffect.CloseScreen)
            TechKeyEvent.RETURN -> updateState { copy(barcode = barcode.dropLast(1)) }
            TechKeyEvent.ENTER -> onBarcodeEntered()
            else -> Unit
        }
    }

    private fun onBarcodeEntered() {
        val barcode = uiState.value.barcode.takeIf { it.isNotBlank() } ?: return
        val docType = uiState.value.document?.docType?.typeNumber ?: return
        val storeCode = uiState.value.document?.storeCode

        updateState {
            val goodDto = repository.requestGood(barcode, docType, storeCode)
            when (goodDto.status) {
                0 -> {
                    //TODO just show update item dialog

                    copy(
                        barcode = "",
                    )
                }

                1 -> {
                    //TODO check if user can create items and show dialog
                    val itemByBarcode = repository.fetchItemByBarcode(barcode, documentId)

                    if (itemByBarcode != null) {
                        //update item dialog
                        repository.saveItem(
                            itemByBarcode.copy(
                                qtyFact = itemByBarcode.qtyFact?.plus(
                                    1.0
                                )
                            )
                        )
                        copy(
                            document = repository.fetchDocumentById(documentId),
                            barcode = ""
                        )
                    } else {
                        copy(
                            dialogItemCreationVisible = true
                        )
                    }
                }

                else -> copy(barcode = "", error = true)
            }
        }
    }
}

