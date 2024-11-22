package com.xetom.wancool.resource

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
class ColorStyle(
    val primary: Color,
    val description: Color,
    val divider: Color,
    val background: Color,
    val placeholder: Color,
    val dialogBackground: Color,
    val iconBackground: Color,
)

val mainColorStyle = ColorStyle(
    primary = Color.Black,
    description = Color.Black.copy(0.7f),
    divider = Color.Black.copy(alpha = 0.2f),
    background = Color.White,
    placeholder = Color.Black.copy(alpha = 0.6f),
    dialogBackground = Color.Black.copy(alpha = 0.68f),
    iconBackground = Color(0xFF6B7280),
)

val LocalColorStyle = staticCompositionLocalOf<ColorStyle> { error("No ColorScheme provided") }