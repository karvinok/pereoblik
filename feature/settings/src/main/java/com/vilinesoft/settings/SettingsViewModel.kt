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
            is UIIntent.CanCreateItemCreatingIfNotExists -> updateState { copy(isCanItemCreatingIfNotExists = !intent.value) }
            is UIIntent.CanCreateItemWithoutBarcodeClick -> updateState { copy(isCanItemCreatingWithoutBarcode = !intent.value) }
            is UIIntent.CanChangeItemNameClick -> updateState { copy(isCanChangeItemName = !intent.value) }
            is UIIntent.ListOfStore -> TODO()
            is UIIntent.SaveClick -> saveSettings()
        }
    }

    private fun saveSettings(){
        manager.ip = UIState().ipString
        manager.prefix = UIState().ipPrefix
        manager.createItemIfNotExists = UIState().isCanItemCreatingIfNotExists
        manager.canCreateItemWithoutBarcode = UIState().isCanItemCreatingWithoutBarcode
        manager.canEditItemName = UIState().isCanChangeItemName
    }
}