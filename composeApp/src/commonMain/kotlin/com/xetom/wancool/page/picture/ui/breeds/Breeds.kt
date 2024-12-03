package com.xetom.wancool.page.picture.ui.breeds

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.xetom.wancool.page.home.business.PictureUIState
import com.xetom.wancool.resource.LocalDimensionStyle
import com.xetom.wancool.widget.OnlineImage

@Composable
fun PictureBreeds(pictures: PictureUIState) {

    val space = Arrangement.spacedBy(LocalDimensionStyle.current.divider)

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(LocalDimensionStyle.current.pagePadding),
        verticalArrangement = space,
        horizontalArrangement = space,
        columns = GridCells.Adaptive(minSize = 480.dp)
    ) {
        itemsIndexed(pictures.breeds) { index, it ->
            OnlineImage(
                url = it.downloadUrl,
                modifier = Modifier.fillMaxWidth().aspectRatio(1f).clickable {

                },
                contentScale = ContentScale.Crop,
            )
        }
    }
}