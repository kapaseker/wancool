package com.xetom.wancool.page.dog.breeds.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import com.xetom.wancool.page.home.business.DogBreedUIState
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

@Composable
fun DogBreeds(
    dogBreeds: DogBreedUIState,
    onDogPage: (main: String, sub: String) -> Unit,
) {

    var dogsList by rememberSaveable { mutableStateOf<ImmutableList<String>>(persistentListOf<String>()) }
    var dogSelectIndex by rememberSaveable { mutableIntStateOf(-1) }
    var dogSelectedMainBreed by rememberSaveable { mutableStateOf("") }

    val dogListState = rememberLazyListState()

    Row(modifier = Modifier.fillMaxSize()) {

        LazyColumn(modifier = Modifier.weight(1f).fillMaxSize(), state = dogListState) {
            itemsIndexed(items = dogBreeds.breeds.keys.toList()) { index, it ->
                Column(modifier = Modifier.height(48.dp).fillMaxWidth().clickable {

                    dogSelectIndex = index
                    dogSelectedMainBreed = it

                    dogBreeds.breeds[it].takeIf { it != null && it.isNotEmpty() }?.let { items ->
                        dogsList = items.toImmutableList()
                    } ?: run {
                        onDogPage(it, "")
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
                onDogPage(dogSelectedMainBreed, dogsList[it])
            }
        }
    }
}