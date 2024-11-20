package com.xetom.wancool.widget

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.drawBackground(
    color: Color
): Modifier {
    return this.drawBehind {
        drawRect(color)
    }
}

fun Modifier.drawBackground(
    brush: Brush
): Modifier {
    return this.drawBehind {
        drawRect(brush)
    }
}