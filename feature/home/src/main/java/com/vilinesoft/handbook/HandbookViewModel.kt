package com.vilinesoft.handbook

import androidx.lifecycle.viewModelScope
import com.vilinesoft.domain.repository.MainRepository
import com.vilinesoft.handbook.HandbookContract.*
import com.vilinesoft.ui.BaseViewModel
import com.vilinesoft.ui.keyprocessing.ArrowEvent
import com.vilinesoft.ui.keyprocessing.KeyEventBus
import com.vilinesoft.ui.keyprocessing.KeyEventHandler
import com.vilinesoft.ui.keyprocessing.TechKeyEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HandbookViewModel(
    override val keyEventBus: KeyEventBus,
    private val repository: MainRepository
): BaseViewModel<UIIntent, UIState, UIEffect>(), KeyEventHandler {

    override var keyEventsJob: Job? = Job()
    override var scope: CoroutineScope = viewModelScope
    override fun provideDefaultState() = UIState()

    override fun handleIntent(intent: UIIntent) {}

    override fun onArrowEvent(event: ArrowEvent) {}

    override fun onNumberEvent(number: Int) {
        updateState {
            copy(barcode = barcode + number.toString())
        }
    }

    override fun onTechKeyEvent(event: TechKeyEvent) {
        when (event) {
            TechKeyEvent.BACKPRESS -> postEffect(UIEffect.CloseScreen)
            TechKeyEvent.RETURN -> updateState { copy(barcode = barcode.dropLast(1)) }
            TechKeyEvent.ENTER -> {
                val barcode = uiState.value.barcode.ifBlank { return }

                viewModelScope.launch(Dispatchers.IO) {
                    updateState { copy(loading = true) }

                    val good = repository.requestGood(barcode, 10, "101").good
                    updateState {
                        copy(barcode = "", loading = false, good = good)
                    }
                }
            }
            else -> Unit
        }
    }
}

