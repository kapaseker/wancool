package com.xetom.wancool.resource

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Stable
class DimensionStyle(
    val titleBar: Dp,
    val pagePadding: Dp,
    val listItemPadding:Dp,
    val cardDescription: TextUnit,
    val divider: Dp,
)

val mainDimensionStyle = DimensionStyle(
    titleBar = 54.dp,
    pagePadding = 10.dp,
    listItemPadding = 16.dp,
    cardDescription = 20.sp,
    divider = 4.dp
)

val LocalDimensionStyle = staticCompositionLocalOf<DimensionStyle> { error("No ColorScheme provided") }