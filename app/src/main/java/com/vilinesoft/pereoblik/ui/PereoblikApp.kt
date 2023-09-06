package com.vilinesoft.pereoblik.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.vilinesoft.navigation.PereoblikNavHost

@Composable
fun PereoblikApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    Column {
        PereoblikNavHost(
            navController = navController,
            onShowSnackbar = { message, action ->
                snackbarHostState.showSnackbar(
                    message = message,
                    actionLabel = action,
                    duration = SnackbarDuration.Short
                ) == SnackbarResult.ActionPerformed
            }
        )
    }
}