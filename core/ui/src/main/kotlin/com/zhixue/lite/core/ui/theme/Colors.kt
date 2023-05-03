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
    primary = Color.Unspecified,
    onPrimary = Color.Unspecified,
    surface = Color.Unspecified,
    onSurface = Color.Unspecified,
    onSurfaceVariant = Color.Unspecified,
    background = Color.Unspecified,
    onBackground = Color.Unspecified,
    onBackgroundVariant = Color.Unspecified,
    error = Color.Unspecified,
    onError = Color.Unspecified,
    outline = Color.Unspecified,
    outlineVariant = Color.Unspecified
)

internal val darkColors = Colors(
    primary = Color.Unspecified,
    onPrimary = Color.Unspecified,
    surface = Color.Unspecified,
    onSurface = Color.Unspecified,
    onSurfaceVariant = Color.Unspecified,
    background = Color.Unspecified,
    onBackground = Color.Unspecified,
    onBackgroundVariant = Color.Unspecified,
    error = Color.Unspecified,
    onError = Color.Unspecified,
    outline = Color.Unspecified,
    outlineVariant = Color.Unspecified
)

val LocalColors = staticCompositionLocalOf { lightColors }