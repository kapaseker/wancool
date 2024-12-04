package com.xetom.wancool.page.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xetom.wancool.LocalNavController
import com.xetom.wancool.load.isLoading
import com.xetom.wancool.nav.DogNav
import com.xetom.wancool.nav.EmptyArg
import com.xetom.wancool.nav.LibraryNav
import com.xetom.wancool.page.cat.ui.breeds.CatBreeds
import com.xetom.wancool.page.dog.breeds.ui.DogBreeds
import com.xetom.wancool.page.home.business.HomeIntent
import com.xetom.wancool.page.home.business.HomeViewModel
import com.xetom.wancool.page.naruto.ui.breeds.NarutoBreeds
import com.xetom.wancool.page.picture.ui.breeds.PictureBreeds
import com.xetom.wancool.resource.LocalColorStyle
import com.xetom.wancool.resource.LocalDimensionStyle
import com.xetom.wancool.widget.drawBackground
import com.xetom.wancool.widget.titleBarShadow
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import wancool.composeapp.generated.resources.Res
import wancool.composeapp.generated.resources.three_dots



@Preview
@Composable
fun PreviewHomePage() {
    HomePage()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage(
    home: HomeViewModel = viewModel { HomeViewModel() },
    scope: CoroutineScope = rememberCoroutineScope(),
) {

    var selectTab by remember { mutableStateOf(0) }

    val columns by home.columns.collectAsState()
    val ip by home.ip.collectAsState()
    val dogBreeds by home.dogBreeds.collectAsState()
    val catBreeds by home.catBreeds.collectAsState()
    val pictures by home.picture.collectAsState()
    val narutos by home.narutos.collectAsState()

    val navController = LocalNavController.current

    fun goLibrary() {
        navController.navigate(LibraryNav.make(EmptyArg))
    }

    fun goDogPage(main: String, sub: String) {
        navController.navigate(DogNav.make(DogNav.Arg(main, sub)))
    }


    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.height(LocalDimensionStyle.current.titleBar).titleBarShadow().drawBackground(LocalColorStyle.current.background)) {
            if (columns.isNotEmpty()) {
                TabRow(
                    selectedTabIndex = selectTab,
                    modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(),
                    backgroundColor = Color.Transparent,
                    contentColor = Color.Black,
                    divider = {

                    }
                ) {
                    columns.withIndex().forEach {
                        Tab(
                            selected = selectTab == it.index,
                            text = {
                                Text(
                                    text = it.value,
                                    color = LocalColorStyle.current.primary
                                )
                            },
                            onClick = {
                                selectTab = it.index
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.fillMaxHeight().aspectRatio(1f).clip(CircleShape).clickable {
                    goLibrary()
                }.padding(14.dp),
                painter = painterResource(Res.drawable.three_dots),
                contentDescription = null,
            )
        }

        Box(modifier = Modifier.fillMaxSize().padding(LocalDimensionStyle.current.pagePadding)) {

            when (selectTab) {

                0 -> {
                    HomeBreeds(dogBreeds.load.isLoading()) {
                        DogBreeds(dogBreeds) { a, b ->
                            goDogPage(main = a, sub = b)
                        }
                    }
                }

                1 -> {
                    HomeBreeds(catBreeds.load.isLoading()) {
                        CatBreeds(catBreeds)
                    }
                }

                2 -> {
                    HomeBreeds(pictures.load.isLoading()) {
                        PictureBreeds(pictures)
                    }
                }

                3 -> {
                    HomeBreeds(narutos.load.isLoading() && narutos.narutos.isEmpty()) {
                        NarutoBreeds(narutos, {
                            home want HomeIntent.MoreNaruto
                        } ) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BoxScope.HomeBreeds(
    loading: Boolean, screen: @Composable () -> Unit
) {
    if (loading) {
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(0.4f).height(4.dp).align(Alignment.Center),
            color = LocalColorStyle.current.primary
        )
    } else {
        screen()
    }
}
