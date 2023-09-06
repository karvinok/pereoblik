package com.vilinesoft.pereoblik

import android.content.Context
import android.content.SharedPreferences
import com.vilinesoft.domain.model.Store
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CacheManager(val context: Context) {

    private val preferences = context.getSharedPreferences(
        PREF_ST_KEY, Context.MODE_PRIVATE
    )

    var ip: String
        get() = get(KEY_BASE_URL, "https://192.168.0.112:8080")
        set(value) {
            save(KEY_BASE_URL, value)
        }

    var prefix: String
        get() = get(KEY_PREFIX, "29")
        set(value) {
            save(KEY_PREFIX, value)
        }

    var createItemIfNotExists: Boolean
        get() = get(KEY_CREATE_ITEM_IF_NOT_EXISTS, false)
        set(value) {
            save(KEY_CREATE_ITEM_IF_NOT_EXISTS, value)
        }

    var canCreateItemWithoutBarcode: Boolean
        get() = get(KEY_CREATE_ITEM_WITHOUT_BARCODE, false)
        set(value) {
            save(KEY_CREATE_ITEM_WITHOUT_BARCODE, value)
        }

    var canEditItemName: Boolean
        get() = get(KEY_CAN_EDIT_ITEM_NAME, false)
        set(value) {
            save(KEY_CAN_EDIT_ITEM_NAME, value)
        }


    var storeCode: Store?
        get() = let {
            val json = get(KEY_STORE_CODE, "")
            Json.decodeFromString<Store>(json)
        }
        set(value) {
            save(KEY_STORE_CODE, Json.encodeToString(value))
        }

    fun get(key: String, defaultValue: String = ""): String {
        return preferences.getString(key, defaultValue)?: defaultValue
    }

    fun get(key: String, defaultValue: Boolean = false): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    fun save(key: String, value: String?) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(key, value?: "")
        editor.apply()
    }

    fun save(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    companion object {
        private const val PREF_ST_KEY = "STORAGE_MAIN_KEY"
        private const val KEY_STORE_CODE = "KEY_STORE_CODE"
        private const val KEY_BASE_URL = "KEY_BASE_URL_PREF"
        private const val KEY_PREFIX = "KEY_PREFIX"
        private const val KEY_CREATE_ITEM_IF_NOT_EXISTS = "KEY_CREATE_ITEM_IF_NOT_EXISTS"
        private const val KEY_CREATE_ITEM_WITHOUT_BARCODE = "KEY_CREATE_ITEM_WITHOUT_BARCODE"
        private const val KEY_CAN_EDIT_ITEM_NAME = "KEY_CAN_EDIT_ITEM_NAME"
    }
}