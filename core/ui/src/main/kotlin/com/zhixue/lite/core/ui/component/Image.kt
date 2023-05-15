package com.zhixue.lite.core.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.AsyncImage

@Composable
fun Image(
    painter: Painter,
    modifier: Modifier = Modifier,
    tint: Color? = null
) {
    Image(
        painter = painter,
        modifier = modifier,
        contentDescription = null,
        colorFilter = if (tint != null) ColorFilter.tint(tint) else null
    )
}

@Composable
fun AsyncImage(
    model: Any?,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = model,
        modifier = modifier,
        contentDescription = null
    )
}