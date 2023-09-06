package com.vilinesoft.ui.keyprocessing

import android.view.KeyEvent
import androidx.compose.ui.input.key.Key
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

interface KeyEventHandler {
    val keyEventBus: KeyEventBus
    var keyEventsJob: Job?
    var scope: CoroutineScope

    fun onKeyEvent(key: Key) {
        when (key) {
            in ArrowEvent.listOfKeys -> onArrowEvent(ArrowEvent.byKey(key))
            in NumEventHelper.listOfKeys -> onNumberEvent(NumEventHelper.byKey(key))
            in TechKeyEvent.listOfKeys -> onTechKeyEvent(TechKeyEvent.byKey(key))
            else -> Unit
        }        
    }

    open fun onArrowEvent(event: ArrowEvent)
    open fun onNumberEvent(number: Int)
    open fun onTechKeyEvent(event: TechKeyEvent)

    fun handleEvent(event: KeyEvent) {
        scope.launch(Dispatchers.IO) {
            keyEventBus.handleKeyEvent(event)
        }
    }

    fun subscribeKeyEvents() {
        keyEventsJob?.cancel()
        keyEventsJob = scope.launch {
            keyEventBus.resetReplayCache()
            keyEventBus.keyEventFlow.collectLatest {
                onKeyEvent(it)
            }
        }
    }

    fun unsubscribeKeyEvents() {
        keyEventsJob?.cancel()
        keyEventsJob = null
    }
}