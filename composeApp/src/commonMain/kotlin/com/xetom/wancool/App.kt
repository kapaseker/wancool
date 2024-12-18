package com.xetom.wancool

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.xetom.wancool.nav.DogNav
import com.xetom.wancool.nav.HomeNav
import com.xetom.wancool.nav.LibraryNav
import com.xetom.wancool.nav.composablePage
import com.xetom.wancool.page.dog.main.ui.DogPage
import com.xetom.wancool.page.home.ui.HomePage
import com.xetom.wancool.page.library.ui.library.LibraryPage
import com.xetom.wancool.resource.LocalColorStyle
import com.xetom.wancool.resource.LocalDimensionStyle
import com.xetom.wancool.resource.Style
import org.jetbrains.compose.ui.tooling.preview.Preview

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found!") }

@Composable
@Preview
fun App() {

    val navController: NavHostController = rememberNavController()

    MaterialTheme {
        CompositionLocalProvider(
            LocalDimensionStyle provides Style.dimension,
            LocalColorStyle provides Style.color,
            LocalNavController provides navController,
        ) {
            NavHost(
                navController = navController,
                startDestination = HomeNav.path,
            ) {
                composablePage(HomeNav) {
                    HomePage()
                }
                composablePage(DogNav) {
                    DogPage(it)
                }
                composablePage(LibraryNav) {
                    LibraryPage()
                }
            }
        }
    }
}
