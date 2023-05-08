package com.zhixue.lite.core.ui.component

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun Text(
    text: String,
    color: Color,
    style: TextStyle,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = style.merge(
            TextStyle(color = color)
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
fun Text(
    text: AnnotatedString,
    color: Color,
    modifier: Modifier = Modifier,
    style: TextStyle = TextStyle.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    BasicText(
        text = text,
        modifier = modifier,
        style = style.merge(
            TextStyle(color = color)
        ),
        maxLines = maxLines,
        overflow = overflow
    )
}