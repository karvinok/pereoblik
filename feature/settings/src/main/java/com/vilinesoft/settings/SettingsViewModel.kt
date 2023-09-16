package com.vilinesoft.settings

import com.vilinesoft.domain.repository.CacheManager
import com.vilinesoft.ui.BaseViewModel
import com.vilinesoft.settings.SettingsContract.*

class SettingsViewModel(private val manager: CacheManager):BaseViewModel<UIIntent, UIState, UIEffect>() {
    override fun provideDefaultState() = UIState(
        ipString = manager.ip,
        ipPrefix = manager.prefix,
        isCanItemCreatingIfNotExists = manager.createItemIfNotExists,
        isCanItemCreatingWithoutBarcode = manager.canCreateItemWithoutBarcode,
        isCanChangeItemName = manager.canEditItemName,
        downloadedStoreList = listOf()
    )
    override fun handleIntent(intent: SettingsContract.UIIntent) {
        when(intent){
            is UIIntent.IpStringChanged -> updateState { copy(isCanChangeItemName = false) }
            is UIIntent.PrefixChanged-> TODO()
            is UIIntent.CanCreateItemsClick -> TODO()
            is UIIntent.CanCreateItemWithoutBarcodeClick -> TODO()
            is UIIntent.CanChangeItemNameClick -> TODO()
            is UIIntent.ListOfStore -> TODO()
            is UIIntent.SaveClick -> TODO()
        }
    }
}