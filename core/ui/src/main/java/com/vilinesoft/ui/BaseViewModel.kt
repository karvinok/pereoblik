package com.vilinesoft.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<I, S, E> : ViewModel() {

    private val defaultState: S by lazy { provideDefaultState() }

    private val _uiIntent: MutableSharedFlow<I> = MutableSharedFlow()

    private val _uiState: MutableStateFlow<S> = MutableStateFlow(defaultState)
    val uiState: StateFlow<S> get() = _uiState.asStateFlow()

    private val _uiEffect: MutableSharedFlow<E> = MutableSharedFlow(replay = 1)
    val uiEffect: SharedFlow<E> get() = _uiEffect.asSharedFlow()

    abstract fun provideDefaultState(): S

    protected fun updateState(stateBlock: suspend S.() -> S) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                stateBlock(it)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    protected fun postEffect(effect: E) {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEffect.emit(effect)
            _uiEffect.resetReplayCache()
        }
    }

    private fun subscribeIntents() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiIntent.collect {
                handleIntent(it)
            }
        }
    }

    /**
     * handle UIIntent
     */
    abstract fun handleIntent(intent: I)
}
