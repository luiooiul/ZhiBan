package com.zhixue.lite.core.ui.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import com.zhixue.lite.core.ui.theme.Theme

@Composable
fun CircularChart(
    value: Float,
    thickness: Dp,
    modifier: Modifier,
    animationDuration: Int = 800,
    lineColor: Color = Theme.colors.primary,
    guildLineColor: Color = Theme.colors.surface
) {
    val sweepAngle = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        sweepAngle.animateTo(-360 * value, tween(animationDuration))
    }

    Canvas(
        modifier = modifier,
    ) {
        drawCircle(
            color = guildLineColor,
            style = Stroke(width = thickness.toPx(), cap = StrokeCap.Butt)
        )
        drawArc(
            color = lineColor,
            startAngle = -90f,
            sweepAngle = sweepAngle.value,
            useCenter = false,
            style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round)
        )
    }
}