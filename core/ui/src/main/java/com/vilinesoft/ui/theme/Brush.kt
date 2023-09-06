package com.vilinesoft.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush

val LinearBrush @Composable get() = Brush.linearGradient(
    colors = listOf(
        MaterialTheme.colorScheme.surface.copy(0.2f),
        MaterialTheme.colorScheme.surface.copy(0.4f)
    ),
    start = Offset.Zero,
    end = Offset(500f, 100f)
)