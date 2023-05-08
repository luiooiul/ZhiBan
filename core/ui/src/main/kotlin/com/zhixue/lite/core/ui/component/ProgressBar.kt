package com.zhixue.lite.core.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zhixue.lite.core.ui.theme.Theme

@Composable
fun ProgressBar(
    value: Float,
    modifier: Modifier,
    cornerRadius: Dp = 16.dp,
    animationDuration: Int = 600,
    color: Color = Theme.colors.primary,
    backgroundColor: Color = Theme.colors.surface
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        progress.animateTo(value, tween(animationDuration))
    }

    Canvas(
        modifier = modifier
    ) {
        drawRoundRect(
            color = backgroundColor,
            cornerRadius = CornerRadius(cornerRadius.toPx())
        )
        drawRoundRect(
            brush = Brush.horizontalGradient(listOf(color, color.copy(0.2f))),
            cornerRadius = CornerRadius(cornerRadius.toPx()),
            size = Size(size.width * progress.value, size.height)
        )
    }
}