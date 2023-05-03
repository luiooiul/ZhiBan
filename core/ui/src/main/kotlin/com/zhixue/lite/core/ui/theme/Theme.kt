package com.zhixue.lite.core.ui.theme

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope

@Composable
fun ZhibanTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColors else lightColors

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalIndication provides ZhibanIndication,
        content = content
    )
}

object Theme {
    val colors: Colors
        @Composable get() = LocalColors.current
    val shapes: Shapes
        @Composable get() = LocalShapes.current
    val typography: Typography
        @Composable get() = LocalTypography.current
}

private object ZhibanIndication : Indication {
    @Composable
    override fun rememberUpdatedInstance(
        interactionSource: InteractionSource
    ): IndicationInstance {
        val color = Theme.colors.onBackground
        val isPressed = interactionSource.collectIsPressedAsState()
        return remember {
            Instance(color, isPressed)
        }
    }

    private class Instance(
        private val color: Color,
        private val isPressed: State<Boolean>
    ) : IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            drawContent()
            if (isPressed.value) {
                drawRect(color = color.copy(alpha = 0.05f), size = size)
            }
        }
    }
}