package com.vilinesoft.ui.keyprocessing

import androidx.compose.ui.input.key.Key

object NumEventHelper {

    val listOfKeys = listOf(
        Key.Zero, Key.One, Key.Two, Key.Three, Key.Four, Key.Five, Key.Six, Key.Seven,
        Key.Eight, Key.Nine, Key.NumPad0, Key.NumPad1, Key.NumPad2, Key.NumPad3, Key.NumPad4,
        Key.NumPad5, Key.NumPad6, Key.NumPad7, Key.NumPad8, Key.NumPad9
    )

    fun byKey(key: Key): Int {
        return when(key) {
            listOfKeys[0] -> 0
            listOfKeys[1] -> 1
            listOfKeys[2] -> 2
            listOfKeys[3] -> 3
            listOfKeys[4] -> 4
            listOfKeys[5] -> 5
            listOfKeys[6] -> 6
            listOfKeys[7] -> 7
            listOfKeys[8] -> 8
            listOfKeys[9] -> 9
            listOfKeys[10] -> 0
            listOfKeys[11] -> 1
            listOfKeys[12] -> 2
            listOfKeys[13] -> 3
            listOfKeys[14] -> 4
            listOfKeys[15] -> 5
            listOfKeys[16] -> 6
            listOfKeys[17] -> 7
            listOfKeys[18] -> 8
            listOfKeys[19] -> 9
            else -> -999
        }
    }
}