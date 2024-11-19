package com.xetom.wancool.style

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
class ColorStyle(
    primary: Color,
    divider: Color,
) {
    val primary = primary
    val divider = divider
}

val mainColorStyle = ColorStyle(Color.Black, Color.Black.copy(alpha = 0.4f))

val LocalColorStyle = staticCompositionLocalOf<ColorStyle> { error("No ColorScheme provided") }