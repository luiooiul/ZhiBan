package com.zhixue.lite.core.ui.component

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(
    color: Color,
    modifier: Modifier = Modifier,
    indicatorNum: Int = 3,
    indicatorSize: Dp = 12.dp,
    indicatorSpacing: Dp = 8.dp,
    indicatorDuration: Int = 600
) {
    val animatedValues = List(indicatorNum) { index ->
        var animatedValue by remember { mutableStateOf(0f) }
        LaunchedEffect(Unit) {
            animate(
                initialValue = 1f,
                targetValue = .2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(indicatorDuration),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(indicatorDuration / indicatorNum * index)
                ),
            ) { value, _ -> animatedValue = value }
        }
        animatedValue
    }

    Row(modifier = modifier) {
        animatedValues.forEach { animatedValue ->
            Spacer(
                modifier = Modifier
                    .padding(horizontal = indicatorSpacing)
                    .width(indicatorSize)
                    .aspectRatio(1f)
                    .graphicsLayer { alpha = animatedValue }
                    .background(color, CircleShape)
            )
        }
    }
}