package com.xetom.wancool

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.xetom.wancool.page.home.ui.HomePage
import com.xetom.wancool.style.LocalColorStyle
import com.xetom.wancool.style.Style
import com.xetom.wancool.style.mainColorStyle
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        CompositionLocalProvider(
            LocalColorStyle provides Style.color
        ) {
            HomePage()
        }
    }
}
