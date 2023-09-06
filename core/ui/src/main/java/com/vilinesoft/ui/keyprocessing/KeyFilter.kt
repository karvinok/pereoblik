package com.vilinesoft.ui.keyprocessing

import android.os.Build
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.key

fun filterPlatformSpecificKey(event: androidx.compose.ui.input.key.KeyEvent): Key {
        val nativeEvent = event.nativeKeyEvent
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            when {
                !nativeEvent.isNumLockOn && nativeEvent.isNumPadArrowEvent() -> Key.Unknown
                else -> event.key
            }
        } else {
            event.key
        }
}

    fun NativeKeyEvent.isNumPadArrowEvent(): Boolean {
        return scanCode != 0 && (keyCode == android.view.KeyEvent.KEYCODE_DPAD_LEFT && scanCode == 75 ||
                keyCode == android.view.KeyEvent.KEYCODE_DPAD_UP && scanCode == 72 ||
                keyCode == android.view.KeyEvent.KEYCODE_DPAD_RIGHT && scanCode == 77 ||
                keyCode == android.view.KeyEvent.KEYCODE_DPAD_DOWN && scanCode == 80)
    }