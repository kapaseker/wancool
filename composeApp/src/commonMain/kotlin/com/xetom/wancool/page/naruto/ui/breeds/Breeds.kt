package com.xetom.wancool.page.naruto.ui.breeds

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.xetom.wancool.page.home.business.NarutoUIState
import com.xetom.wancool.resource.LocalDimensionStyle
import com.xetom.wancool.widget.OnlineImage
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun NarutoBreeds(
    naruto: NarutoUIState,
    onMore: () -> Unit,
    onClick: (Int) -> Unit,
) {
    val space = Arrangement.spacedBy(LocalDimensionStyle.current.divider)

    val gridState = rememberLazyGridState()
    val narutoSize by rememberUpdatedState(naruto.narutos.size)

    LaunchedEffect(Unit) {
        snapshotFlow { gridState.firstVisibleItemIndex }.combine(snapshotFlow { gridState.layoutInfo.visibleItemsInfo.size }) { a, b ->
            a to b
        }.distinctUntilChanged().collectLatest {
            val (first, size) = it
            if ((narutoSize - 2) < (first + size)) {
                println("OnMore")
                onMore.invoke()
            }
        }
    }

    LazyVerticalGrid(
        state = gridState,
        modifier = Modifier.fillMaxSize().padding(LocalDimensionStyle.current.pagePadding),
        verticalArrangement = space,
        horizontalArrangement = space,
        columns = GridCells.Adaptive(minSize = 160.dp)
    ) {
        itemsIndexed(naruto.narutos) { index, it ->
            Box(modifier = Modifier.fillMaxSize()) {

                OnlineImage(
                    url = it.images.firstOrNull().orEmpty(),
                    modifier = Modifier.fillMaxWidth().aspectRatio(1f).clickable {
                        onClick.invoke(index)
                    },
                    contentScale = ContentScale.Crop,
                )

                Text(
                    text = it.name, modifier = Modifier.background(Color.Black.copy(alpha = 0.6f)), color = Color.White
                )
            }
        }
    }
}