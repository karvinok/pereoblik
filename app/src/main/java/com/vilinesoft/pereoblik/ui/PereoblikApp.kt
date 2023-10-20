package com.vilinesoft.pereoblik.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.vilinesoft.navigation.PereoblikNavHost

@Composable
fun PereoblikApp() {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()

    Column {
        PereoblikNavHost(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.background
            ),
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