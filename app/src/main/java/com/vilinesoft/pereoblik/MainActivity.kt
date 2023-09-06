package com.vilinesoft.pereoblik

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vilinesoft.pereoblik.ui.PereoblikApp
import com.vilinesoft.ui.theme.PereoblikTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PereoblikTheme {
                PereoblikApp()
            }
        }
    }
}