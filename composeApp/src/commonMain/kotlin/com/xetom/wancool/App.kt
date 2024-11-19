package com.xetom.wancool

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.xetom.wancool.page.home.ui.HomePage
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        HomePage()
    }
}
