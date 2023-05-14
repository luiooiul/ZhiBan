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
    val part: Color,
    val onPart: Color,
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
    part = Color(0xFFF1E05A),
    onPart = Color.White,
    error = Color(0xFFF53536),
    onError = Color.White,
    outline = Color(0xFFF4F6F8),
    outlineVariant = Color(0xFFF4F6F8)
)

internal val darkColors = Colors(
    primary = Color(0xFF41B883),
    onPrimary = Color.White,
    surface = Color(0xFF242328),
    onSurface = Color(0xFFC9D1D9),
    onSurfaceVariant = Color(0xFF7D8590),
    background = Color(0xFF1B1A1D),
    onBackground = Color(0xFFE6EDF3),
    onBackgroundVariant = Color(0xFF7D8590),
    part = Color(0xFFF1E05A),
    onPart = Color.White,
    error = Color(0xFFE34C26),
    onError = Color.White,
    outline = Color(0xFF30363D),
    outlineVariant = Color(0xFF30363D)
)

val LocalColors = staticCompositionLocalOf { lightColors }