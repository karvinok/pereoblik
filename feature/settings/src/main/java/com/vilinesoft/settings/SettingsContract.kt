package com.vilinesoft.settings

import com.vilinesoft.domain.model.Store

class SettingsContract {

    data class UIState(
        val ipString: String,
        val ipPrefix: String,
        val isCanItemCreatingIfNotExists: Boolean,
        val isCanItemCreatingWithoutBarcode: Boolean,
        val isCanChangeItemName: Boolean,
        val downloadedStoreList: List<Store>,
        val loadingStoreList: Boolean = false,
        val internetConnection: Boolean = true,
    )

    sealed class UIIntent {
        data class IpStringChanged(val value: String) : UIIntent()
        data class PrefixChanged(val value: String) : UIIntent()
        data class CanCreateItemsClick(val value: Boolean) : UIIntent()
        data class CanCreateItemWithoutBarcodeClick(val value: Boolean) : UIIntent()
        data class CanChangeItemNameClick(val value: Boolean) : UIIntent()
        data object ListOfStore : UIIntent()
        data object SaveClick : UIIntent()
    }

    sealed class UIEffect {
        data object CloseScreen : UIEffect()
    }

}