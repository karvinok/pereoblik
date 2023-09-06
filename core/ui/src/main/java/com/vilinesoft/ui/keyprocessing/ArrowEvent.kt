
package com.vilinesoft.ui.keyprocessing

import androidx.compose.ui.input.key.Key

enum class ArrowEvent {
    LEFT, UP, RIGHT, DOWN;

    companion object {
        val listOfKeys = listOf(
            Key.DirectionLeft, Key.DirectionUp, Key.DirectionRight, Key.DirectionDown
        )

        fun byKey(key: Key): ArrowEvent {
            return when(key) {
                listOfKeys[0] -> LEFT
                listOfKeys[1] -> UP
                listOfKeys[2] -> RIGHT
                listOfKeys[3] -> DOWN
                else -> DOWN
            }
        }
    }
}