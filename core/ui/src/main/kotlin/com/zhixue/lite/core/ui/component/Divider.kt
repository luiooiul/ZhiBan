package com.zhixue.lite.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zhixue.lite.core.ui.theme.Theme

@Composable
fun HorizontalDivider(
    modifier: Modifier = Modifier,
    height: Dp = 1.dp,
    color: Color = Theme.colors.outlineVariant,
    spacing: Dp = 0.dp
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .padding(horizontal = spacing)
            .background(color)
    )
}