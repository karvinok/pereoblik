package com.vilinesoft.ui.keyprocessing

import android.view.KeyEvent
import androidx.compose.ui.input.key.Key
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class KeyEventBus {
    private val _keyEventFlow = MutableSharedFlow<Key>(replay = 1)
    val keyEventFlow: SharedFlow<Key> = _keyEventFlow.asSharedFlow()

    fun resetReplayCache() {
        _keyEventFlow.resetReplayCache()
    }

    suspend fun handleKeyEvent(event: KeyEvent?) {
        if (event?.action == KeyEvent.ACTION_DOWN) {
            val filteredKey = filterPlatformSpecificKey(androidx.compose.ui.input.key.KeyEvent(event))
            _keyEventFlow.emit(filteredKey)
        }
    }
}