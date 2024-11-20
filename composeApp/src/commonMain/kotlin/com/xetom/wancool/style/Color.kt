package com.xetom.wancool.style

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
class ColorStyle(
    val primary: Color,
    val divider: Color,
    val background: Color,
    val placeholder: Color,
)

val mainColorStyle = ColorStyle(primary = Color.Black, divider = Color.Black.copy(alpha = 0.2f), background = Color.White, placeholder = Color.Black.copy(alpha = 0.6f))

val LocalColorStyle = staticCompositionLocalOf<ColorStyle> { error("No ColorScheme provided") }