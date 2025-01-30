package com.sashamprog.carhub.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4878F6),  // основний колір #4878F6
    secondary = Color(0xFFB5C3E2),  // додатковий колір #B5C3E2
    tertiary = Color(0xFF1D1D1D),  // третинний колір #1D1D1D
    background = Color(0xFF101010),  // фон #101010
    surface = Color(0xFF1D1D1D),  // поверхня #1D1D1D
    onPrimary = Color.White,  // текст на основному кольорі
    onSecondary = Color(0xFF101010),  // текст на другорядному кольорі
    onBackground = Color(0xFFF0F1F3),  // текст на фоні #F0F1F3
    onSurface = Color(0xFFF0F1F3),  // текст на поверхні
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4878F6),  // основний колір #4878F6
    secondary = Color(0xFFB5C3E2),  // додатковий колір #B5C3E2
    tertiary = Color(0xFF101010),  // третинний колір #101010
    background = Color(0xFFF0F1F3),  // фон #F0F1F3
    surface = Color(0xFFFFFFFF),  // поверхня біла
    onPrimary = Color.White,  // текст на основному кольорі
    onSecondary = Color(0xFF101010),  // текст на другорядному кольорі
    onBackground = Color(0xFF1D1D1D),  // текст на фоні #1D1D1D
    onSurface = Color(0xFF101010),  // текст на поверхні
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}