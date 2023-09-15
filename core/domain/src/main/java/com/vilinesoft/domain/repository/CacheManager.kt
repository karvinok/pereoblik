package com.vilinesoft.domain.repository

import com.vilinesoft.domain.model.Store

interface CacheManager {
    var ip: String
    var prefix: String
    var createItemIfNotExists: Boolean
    var canCreateItemWithoutBarcode: Boolean
    var canEditItemName: Boolean
    var storeCode: Store?
}