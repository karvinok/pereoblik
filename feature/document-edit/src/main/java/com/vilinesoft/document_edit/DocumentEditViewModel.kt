package com.vilinesoft.document_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vilinesoft.document_edit.DocumentEditContract.UIEffect
import com.vilinesoft.document_edit.DocumentEditContract.UIIntent
import com.vilinesoft.document_edit.DocumentEditContract.UIState
import com.vilinesoft.document_edit.DocumentEditContract.UpdateItemDialogIntent
import com.vilinesoft.document_edit.DocumentEditContract.UpdateItemDialogState
import com.vilinesoft.domain.model.DocumentItem
import com.vilinesoft.domain.repository.CacheManager
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
    private val repository: MainRepository,
    private val cacheManager: CacheManager
) : BaseViewModel<UIIntent, UIState, UIEffect>(), KeyEventHandler {

    override var keyEventsJob: Job? = Job()
    override var scope: CoroutineScope = viewModelScope
    override fun provideDefaultState() = UIState()
    private val DEFAULT_ITEM_EDIT_COUNT: Double = 1.0

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

                val state = item.createStateByItem(
                    canEditItemName = cacheManager.canEditItemName || item.name.isNullOrBlank()
                ).copy(
                    id = item.id,
                    count = item.qtyFact,
                    isMode3 = true
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

            is UpdateItemDialogIntent.DialogConfirmClick -> handleUpdateItemDialogConfirmClick()

            is UpdateItemDialogIntent.DialogDismiss -> updateState {
                copy(dialogUpdateItemState = null)
            }

            else -> Unit
        }
    }

    private fun handleUpdateItemDialogConfirmClick() {
        updateState {
            dialogUpdateItemState?.let {
                val item = DocumentItem(
                    id = it.id,
                    idDoc = documentId,
                    barcode = it.barcode,
                    price = it.price,
                    name = it.itemName,
                    unit = "шт",
                    isNew = false,
                    codeGoods1C = "",
                    qtyBalance = it.qtyBalance,
                    qtyFact = if (!it.isMode3 && it.qtyFact != null)
                        it.count?.plus(it.qtyFact) else it.count,
                )
                repository.saveItem(item)
                val document = repository.fetchDocumentById(documentId)
                copy(
                    dialogUpdateItemState = null,
                    barcode = "",
                    document = document
                )
            } ?: run {
                copy()
            }
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
            if (dialogUpdateItemState == null) {
                copy(
                    barcode = barcode + number.toString(),
                    error = false
                )
            } else {
                val newCountString = if (dialogUpdateItemState.isCountEditedLocally) {
                    (dialogUpdateItemState.count?.toInt() ?: 0).toString() + number.toString()
                } else number.toString()
                copy(
                    dialogUpdateItemState = dialogUpdateItemState.copy(
                        count = newCountString.toDouble(),
                        isCountEditedLocally = true
                    ),
                )
            }
        }
    }

    override fun onTechKeyEvent(event: TechKeyEvent) {
        when (event) {
            TechKeyEvent.BACKPRESS -> onBackPressed()
            TechKeyEvent.RETURN -> onReturnPressed()
            TechKeyEvent.ENTER -> onEnterPressed()
            else -> Unit
        }
    }

    private fun onBackPressed() {
        updateState {
            if (dialogUpdateItemState != null) {
                copy(dialogUpdateItemState = null)
            } else {
                postEffect(UIEffect.CloseScreen)
                copy()
            }
        }
    }

    private fun onReturnPressed() {
        updateState {
            if (dialogUpdateItemState == null) {
                copy(barcode = barcode.dropLast(1))
            } else {
                val newCountString = dialogUpdateItemState.count?.toInt().toString()
                val newDoubleOrNull = newCountString.dropLast(1).toDoubleOrNull()
                if (newDoubleOrNull == null) {
                    val value =
                        if (dialogUpdateItemState.isMode3) dialogUpdateItemState.qtyFact else DEFAULT_ITEM_EDIT_COUNT
                    copy(
                        dialogUpdateItemState = dialogUpdateItemState.copy(
                            count = value,
                            isCountEditedLocally = false
                        )
                    )
                } else {
                    copy(
                        dialogUpdateItemState = dialogUpdateItemState.copy(
                            count = newDoubleOrNull
                        )
                    )
                }
            }
        }
    }

    private fun onEnterPressed() {
        val isEnterPressedOnDialog = uiState.value.dialogUpdateItemState != null

        if (isEnterPressedOnDialog) {
            handleDialogEnterPress()
        } else {
            handleEnterPress()
        }
    }

    private fun handleDialogEnterPress() {
        handleIntent(UpdateItemDialogIntent.DialogConfirmClick)
    }

    private fun handleEnterPress() {
        val barcode = uiState.value.barcode.takeIf { it.isNotBlank() } ?: return
        val docType = uiState.value.document?.docType?.typeNumber ?: return
        val storeCode = uiState.value.document?.storeCode

        updateState {
            val goodDto = repository.requestGood(barcode, docType, storeCode)

            val item = repository.fetchItemByBarcode(barcode, documentId)
            val canEditItemName = cacheManager.canEditItemName

            val state = item?.createStateByItem(
                canEditItemName = canEditItemName || item.name.isNullOrBlank()
            )?.copy(
                id = item.id,
                count = DEFAULT_ITEM_EDIT_COUNT,
                isMode3 = false
            )

            when (goodDto.status) {
                0 -> {
                    //TODO just show update item dialog NOT MODE 3
                    copy(
                        dialogUpdateItemState = state,
                        barcode = ""
                    )
                }

                1 -> {
                    if (item != null) {
                        copy(
                            dialogUpdateItemState = state,
                            barcode = ""
                        )
                    } else {
                        if (cacheManager.createItemIfNotExists) {
                            copy(
                                dialogItemCreationVisible = true
                            )
                        } else copy(error = true)
                    }
                }

                else -> copy(barcode = "NOT FOUND", error = true)
            }
        }
    }
}

fun DocumentItem.createStateByItem(canEditItemName: Boolean): UpdateItemDialogState {
    return UpdateItemDialogState(
        id = id,
        itemName = name ?: "",
        isNameEditable = canEditItemName,
        qtyFact = qtyFact,
        qtyBalance = qtyBalance,
        barcode = barcode ?: "",
        count = qtyFact,
        price = price,
        unitType = unit,
        isMode3 = false
    )
}

