package com.xetom.wancool.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

object Style {
    val color: ColorStyle by mutableStateOf(mainColorStyle)
}