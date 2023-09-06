package com.vilinesoft.ui.theme

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

object Colors {
    val White = Color(0xFFFFFFFF)
    val Black = Color(0xFF000000)

    val BackgroundDark = Color(0xFF1E1E1E)
    val SurfaceDark = Color(0xFF7E7E7E)
    val PrimaryDark = Color.White
    val SecondaryDark = Color(0xFF2757CF)

    val BackgroundLight = Color(0xFF1E1E1E)
    val SurfaceLight = Color(0xFF666666)
    val PrimaryLight = Color.White
    val SecondaryLight = Color(0xFF5278d8)
}

private val darkColors = darkColorScheme(
    background = Colors.BackgroundDark,
    primary = Colors.PrimaryDark,
    secondary = Colors.SecondaryDark,
    surface = Colors.SurfaceDark,
)
private val lightColors = lightColorScheme(
    background = Colors.BackgroundLight,
    primary = Colors.PrimaryLight,
    secondary = Colors.SecondaryLight,
    surface = Colors.SurfaceLight
)

@Composable
fun PereoblikTheme(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val contentColor = if (isDarkTheme) darkColors.primary else lightColors.primary
    MaterialTheme(
        colorScheme = if (isDarkTheme) darkColors else lightColors,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = {
            CompositionLocalProvider(LocalContentColor provides contentColor) {
                content()
            }
        }
    )
}