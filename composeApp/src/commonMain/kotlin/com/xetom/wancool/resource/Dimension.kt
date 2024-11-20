package com.xetom.wancool.resource

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class DimensionStyle(
    val titleBar: Dp,
    val pagePadding: Dp,
)

val mainDimensionStyle = DimensionStyle(titleBar = 54.dp, pagePadding = 10.dp)

val LocalDimensionStyle = staticCompositionLocalOf<DimensionStyle> { error("No ColorScheme provided") }