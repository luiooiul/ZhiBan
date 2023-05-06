package com.zhixue.lite.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class Colors(
    val primary: Color,
    val onPrimary: Color,
    val surface: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val background: Color,
    val onBackground: Color,
    val onBackgroundVariant: Color,
    val error: Color,
    val onError: Color,
    val outline: Color,
    val outlineVariant: Color
)

internal val lightColors = Colors(
    primary = Color(0xFF50EBAA),
    onPrimary = Color.White,
    surface = Color(0xFFF4F6F8),
    onSurface = Color(0xFF0F0F0F),
    onSurfaceVariant = Color(0xFF989898),
    background = Color.White,
    onBackground = Color(0xFF0F0F0F),
    onBackgroundVariant = Color.LightGray,
    error = Color(0xFFF53536),
    onError = Color.White,
    outline = Color(0xFFF4F6F8),
    outlineVariant = Color(0xFFF4F6F8)
)

internal val darkColors = Colors(
    primary = Color(0xFF238636),
    onPrimary = Color.White,
    surface = Color(0xFF242328),
    onSurface = Color.White,
    onSurfaceVariant = Color.LightGray,
    background = Color(0xFF1B1A1D),
    onBackground = Color.White,
    onBackgroundVariant = Color(0xFF7D7D7D),
    error = Color(0xFFF84C48),
    onError = Color.White,
    outline = Color(0xFF242328),
    outlineVariant = Color(0xFF242328)
)

val LocalColors = staticCompositionLocalOf { lightColors }