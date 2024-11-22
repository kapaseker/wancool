package com.xetom.wancool.page.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xetom.wancool.LocalNavController
import com.xetom.wancool.load.isLoading
import com.xetom.wancool.nav.DogNav
import com.xetom.wancool.page.dog.home_list.ui.DogListScreen
import com.xetom.wancool.page.home.business.HomeViewModel
import com.xetom.wancool.resource.LocalColorStyle
import com.xetom.wancool.resource.LocalDimensionStyle
import com.xetom.wancool.widget.Gallery
import com.xetom.wancool.widget.OnlineImage
import com.xetom.wancool.widget.drawBackground
import com.xetom.wancool.widget.titleBarShadow
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import wancool.composeapp.generated.resources.Res
import wancool.composeapp.generated.resources.dog_bone
import wancool.composeapp.generated.resources.prefix_life_span
import wancool.composeapp.generated.resources.prefix_temperament
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

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.height(LocalDimensionStyle.current.titleBar).titleBarShadow().drawBackground(LocalColorStyle.current.background)) {
            if (columns.isNotEmpty()) {
                TabRow(
                    selectedTabIndex = selectTab, modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(), backgroundColor = Color.Transparent, contentColor = Color.Black, divider = {

                    }) {
                    columns.withIndex().forEach {
                        Tab(selectTab == it.index, text = {
                            Text(text = it.value, color = LocalColorStyle.current.primary)
                        }, onClick = {
                            selectTab = it.index
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.fillMaxHeight().aspectRatio(1f).clip(CircleShape).clickable {

                }.padding(14.dp),
                painter = painterResource(Res.drawable.three_dots),
                contentDescription = null,
            )
        }

        var dogsList by rememberSaveable { mutableStateOf<ImmutableList<String>>(persistentListOf<String>()) }
        var dogSelectIndex by rememberSaveable { mutableIntStateOf(-1) }
        var dogSelectedMainBreed by rememberSaveable { mutableStateOf("") }

        val dogListState = rememberLazyListState()
        val catListState = rememberLazyListState()
        val navController = LocalNavController.current

        fun goDogPage(main: String, sub: String) {
            navController.navigate(DogNav.make(DogNav.Arg(main, sub)))
        }

        Box(modifier = Modifier.fillMaxSize().padding(LocalDimensionStyle.current.pagePadding)) {
            when (selectTab) {
                0 -> {
                    if (dogBreeds.load.isLoading()) {

                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth(0.4f).height(4.dp).align(Alignment.Center), color = LocalColorStyle.current.primary)

                    } else {
                        Row(modifier = Modifier.fillMaxSize()) {

                            LazyColumn(modifier = Modifier.weight(1f).fillMaxSize(), state = dogListState) {
                                itemsIndexed(items = dogBreeds.breeds.keys.toList()) { index, it ->
                                    Column(modifier = Modifier.height(48.dp).fillMaxWidth().clickable {

                                        dogSelectIndex = index
                                        dogSelectedMainBreed = it

                                        dogBreeds.breeds[it].takeIf { it != null && it.isNotEmpty() }?.let { items ->
                                            dogsList = items.toImmutableList()
                                        } ?: run {
                                            goDogPage(main = it, sub = "")
                                        }
                                    }) {
                                        Box(modifier = Modifier.fillMaxWidth().weight(1f).padding(horizontal = 16.dp)) {

                                            Text(
                                                text = it, color = LocalColorStyle.current.primary, fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterStart)
                                            )

                                            if (dogSelectIndex == index) {
                                                Image(
                                                    painter = painterResource(Res.drawable.dog_bone), modifier = Modifier.size(32.dp).align(Alignment.CenterEnd), contentDescription = null
                                                )
                                            }
                                        }
                                        Spacer(
                                            modifier = Modifier.fillMaxWidth().height(1.dp).padding(horizontal = 16.dp).background(LocalColorStyle.current.divider)
                                        )
                                    }
                                }
                            }

                            if (dogsList.isNotEmpty()) {
                                Spacer(modifier = Modifier.width(2.dp).fillMaxHeight().background(LocalColorStyle.current.divider))
                                DogListScreen(modifier = Modifier.weight(2f).fillMaxSize(), dogs = dogsList) {
                                    goDogPage(main = dogSelectedMainBreed, sub = dogsList[it])
                                }
                            }
                        }
                    }
                }

                1 -> {
                    if (catBreeds.load.isLoading()) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth(0.4f).height(4.dp).align(Alignment.Center), color = LocalColorStyle.current.primary)
                    } else {

                        var catImage by remember { mutableStateOf("") }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = catListState,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            itemsIndexed(items = catBreeds.breeds) { index, it ->

                                Column(
                                    modifier = Modifier.fillMaxWidth().border(
                                        width = 1.dp,
                                        color = LocalColorStyle.current.divider,
                                        shape = RoundedCornerShape(6.dp)
                                    ).padding(horizontal = LocalDimensionStyle.current.listItemPadding, vertical = 10.dp)
                                ) {

                                    Row(modifier = Modifier.fillMaxWidth().height(128.dp)) {

                                        OnlineImage(
                                            url = it.image?.url.orEmpty(),
                                            modifier = Modifier.fillMaxHeight().aspectRatio(1f).clip(RoundedCornerShape(10.dp)).clickable {
                                                it.image?.url?.let{
                                                    catImage = it
                                                }
                                            },
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop
                                        )

                                        Column(modifier = Modifier.padding(start = 20.dp).fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {

                                            Row(verticalAlignment = Alignment.Bottom) {

                                                Text(
                                                    text = it.name, color = LocalColorStyle.current.primary, fontSize = 28.sp, fontWeight = FontWeight.Bold
                                                )

                                                Text(
                                                    text = it.origin,
                                                    color = LocalColorStyle.current.description,
                                                    fontSize = LocalDimensionStyle.current.cardDescription,
                                                    modifier = Modifier.padding(start = 6.dp)
                                                )
                                            }

                                            Text(
                                                text = stringResource(Res.string.prefix_life_span, it.lifeSpan),
                                                color = LocalColorStyle.current.primary,
                                                fontSize = LocalDimensionStyle.current.cardDescription,
                                            )

                                            Text(
                                                text = stringResource(Res.string.prefix_temperament, it.temperament),
                                                color = LocalColorStyle.current.primary,
                                                fontSize = LocalDimensionStyle.current.cardDescription,
                                            )
                                        }
                                    }

                                    Text(
                                        modifier = Modifier.padding(top = 10.dp),
                                        text = it.description,
                                        fontSize = LocalDimensionStyle.current.cardDescription,
                                        color = LocalColorStyle.current.description
                                    )
                                }
                            }
                        }

                        val showCat by remember { derivedStateOf {
                            catImage.isNotEmpty()
                        } }

                        if (showCat) {
                            Gallery(modifier = Modifier.fillMaxSize(), images = persistentListOf(catImage)) {
                                catImage = ""
                            }
                        }
                    }
                }
            }
        }
    }
}
