package com.vilinesoft.settings

import com.vilinesoft.domain.repository.CacheManager
import com.vilinesoft.ui.BaseViewModel
import com.vilinesoft.settings.SettingsContract.*

class SettingsViewModel(private val manager: CacheManager) :
    BaseViewModel<UIIntent, UIState, UIEffect>() {
    override fun provideDefaultState() = UIState()

    init {
        updateState {
            copy(
                ipString = manager.ip,
                ipPrefix = manager.prefix,
                isCanItemCreatingIfNotExists = manager.createItemIfNotExists,
                isCanItemCreatingWithoutBarcode = manager.canCreateItemWithoutBarcode,
                isCanChangeItemName = manager.canEditItemName,
            )
        }
    }

    override fun handleIntent(intent: UIIntent) {
        when (intent) {
            is UIIntent.IpStringChanged -> updateState { copy(ipString = intent.value) }
            is UIIntent.PrefixChanged -> updateState { copy(ipPrefix = intent.value) }
            is UIIntent.CanCreateItemCreatingIfNotExists -> updateState {
                copy(
                    isCanItemCreatingIfNotExists = !intent.value
                )
            }

            is UIIntent.CanCreateItemWithoutBarcodeClick -> updateState {
                copy(
                    isCanItemCreatingWithoutBarcode = !intent.value
                )
            }

            is UIIntent.CanChangeItemNameClick -> updateState { copy(isCanChangeItemName = !intent.value) }
            is UIIntent.SelectStore -> updateState { copy(storeSelected = intent.value) }
            is UIIntent.SaveClick -> saveSettings()
        }
    }

    private fun saveSettings() {
        manager.ip = uiState.value.ipString
        manager.prefix = uiState.value.ipPrefix
        manager.createItemIfNotExists = uiState.value.isCanItemCreatingIfNotExists
        manager.canCreateItemWithoutBarcode = uiState.value.isCanItemCreatingWithoutBarcode
        manager.canEditItemName = uiState.value.isCanChangeItemName
    }
}