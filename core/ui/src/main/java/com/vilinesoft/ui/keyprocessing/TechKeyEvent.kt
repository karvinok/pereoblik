package com.vilinesoft.ui.keyprocessing

import androidx.compose.ui.input.key.Key

enum class TechKeyEvent {
    RETURN,
    ENTER,
    ESC,
    NONE,
    F1,
    F2,
    F3,
    F4,

    ;

    companion object {
        val listOfKeys = listOf(
            Key.Backspace,
            Key.Enter,
            Key.Escape,
            Key.F1,
            Key.F2,
            Key.F3,
            Key.F4,
            Key.Unknown
        )

        fun byKey(key: Key): TechKeyEvent {
            return when(key) {
                listOfKeys[0] -> RETURN
                listOfKeys[1] -> ENTER
                listOfKeys[2] -> ESC
                listOfKeys[3] -> F1
                listOfKeys[4] -> F2
                listOfKeys[5] -> F3
                listOfKeys[6] -> F4
                else -> NONE
            }
        }
    }
}