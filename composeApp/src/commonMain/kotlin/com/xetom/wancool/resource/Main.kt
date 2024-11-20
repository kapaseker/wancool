package com.xetom.wancool.resource

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

object Style {
    val color: ColorStyle by mutableStateOf(mainColorStyle)
    val dimension: DimensionStyle by mutableStateOf(mainDimensionStyle)
}