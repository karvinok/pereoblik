package com.vilinesoft.document_edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vilinesoft.document_edit.DocumentEditContract.*
import com.vilinesoft.domain.repository.MainRepository
import com.vilinesoft.ui.BaseViewModel
import com.vilinesoft.ui.keyprocessing.ArrowEvent
import com.vilinesoft.ui.keyprocessing.KeyEventBus
import com.vilinesoft.ui.keyprocessing.KeyEventHandler
import com.vilinesoft.ui.keyprocessing.TechKeyEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DocumentEditViewModel(
    savedStateHandle: SavedStateHandle,
    override val keyEventBus: KeyEventBus,
    private val repository: MainRepository
): BaseViewModel<UIIntent, UIState, UIEffect>(), KeyEventHandler {

    override var keyEventsJob: Job? = Job()
    override var scope: CoroutineScope = viewModelScope
    override fun provideDefaultState() = UIState()

    init {
        val documentId: String = checkNotNull(savedStateHandle["document_id"])
        updateState {
            copy(document = repository.fetchDocumentById(documentId))
        }
    }

    override fun handleIntent(intent: UIIntent) {}

    override fun onArrowEvent(event: ArrowEvent) {}

    override fun onNumberEvent(number: Int) {}

    override fun onTechKeyEvent(event: TechKeyEvent) {
        when(event) {
            TechKeyEvent.BACKPRESS -> postEffect(UIEffect.CloseScreen)
            else -> Unit
        }
    }
}

