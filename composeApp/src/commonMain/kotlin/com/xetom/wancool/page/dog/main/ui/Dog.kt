package com.xetom.wancool.page.dog.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.xetom.wancool.LocalNavController
import com.xetom.wancool.load.isLoading
import com.xetom.wancool.page.dog.main.business.DogViewModel
import com.xetom.wancool.resource.LocalColorStyle
import com.xetom.wancool.resource.LocalDimensionStyle
import com.xetom.wancool.widget.Gallery
import com.xetom.wancool.widget.OnlineImage
import com.xetom.wancool.widget.TitleBar

@Composable
fun DogPage(
    entry: NavBackStackEntry,
    dog: DogViewModel = viewModel { DogViewModel(entry.savedStateHandle) },
) {
    var clickIndex by remember { mutableStateOf(-1) }
    val navController: NavController = LocalNavController.current

    val dogs by dog.dogs.collectAsState()
    val title by dog.title.collectAsState()

    val showGallery by remember {
        derivedStateOf {
            clickIndex >= 0
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        TitleBar(title = title) {
            if (clickIndex > -1) {
                clickIndex = -1
            } else {
                navController.navigateUp()
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (dogs.load.isLoading()) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth(0.4f).height(4.dp).align(Alignment.Center), color = LocalColorStyle.current.primary)
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize().padding(LocalDimensionStyle.current.pagePadding),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    columns = GridCells.Adaptive(minSize = 240.dp)
                ) {
                    itemsIndexed(dogs.dogs) { index, it ->
                        OnlineImage(
                            url = it,
                            modifier = Modifier.fillMaxWidth().aspectRatio(1f).clickable {
                                clickIndex = index
                            },
                            contentScale = ContentScale.Crop,
                        )
                    }
                }

                if (showGallery) {
                    Gallery(
                        modifier = Modifier.fillMaxSize(), images = dogs.dogs, selected = clickIndex
                    ) {
                        clickIndex = -1
                    }
                }
            }
        }
    }
}