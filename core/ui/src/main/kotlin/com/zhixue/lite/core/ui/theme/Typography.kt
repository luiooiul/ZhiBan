package com.zhixue.lite.core.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Immutable
data class Typography(
    val headline: TextStyle = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
    ),
    val titleMedium: TextStyle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.15.sp
    ),
    val titleSmall: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.1.sp
    ),
    val subtitleSmall: TextStyle = TextStyle(
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    val body: TextStyle = TextStyle(
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    val label: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.5.sp
    ),
    val button: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        letterSpacing = 0.1.sp
    )
)

val LocalTypography = staticCompositionLocalOf { Typography() }