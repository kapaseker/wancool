package com.xetom.wancool.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.xetom.wancool.resource.LocalColorStyle
import com.xetom.wancool.resource.LocalDimensionStyle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.skia.ColorFilter
import wancool.composeapp.generated.resources.Res
import wancool.composeapp.generated.resources.chevron_left
import wancool.composeapp.generated.resources.chevron_right
import wancool.composeapp.generated.resources.close

@Composable
fun Gallery(
    modifier: Modifier = Modifier,
    images: ImmutableList<String>,
    selected: Int = 0,
    scope: CoroutineScope = rememberCoroutineScope(),
    onClose: () -> Unit,
) {

    val pagerState = rememberPagerState(initialPage = selected) { images.size }

    var selectPage by remember { mutableIntStateOf(selected) }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }.distinctUntilChanged().collectLatest { page ->
            selectPage = page
        }
    }

    fun goPage(index: Int) {
        scope.launch {
            pagerState.scrollToPage(index)
        }
    }

    Box(modifier = modifier.drawBackground(LocalColorStyle.current.dialogBackground)) {

        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { index ->
            OnlineImage(url = images[index], contentDescription = null, modifier = Modifier.fillMaxSize())
        }

        Image(
            painter = painterResource(Res.drawable.chevron_left),
            contentDescription = null,
            modifier = Modifier.padding(start = LocalDimensionStyle.current.pagePadding).size(68.dp).clip(CircleShape).align(androidx.compose.ui.Alignment.CenterStart)
                .drawBackground(LocalColorStyle.current.iconBackground).clickable {
                    goPage(pagerState.currentPage - 1)
                }.padding(16.dp)
        )

        Image(
            painter = painterResource(Res.drawable.chevron_right),
            contentDescription = null,
            modifier = Modifier.padding(end = LocalDimensionStyle.current.pagePadding).size(68.dp).clip(CircleShape).align(androidx.compose.ui.Alignment.CenterEnd)
                .drawBackground(LocalColorStyle.current.iconBackground).clickable {
                    goPage(pagerState.currentPage + 1)
                }.padding(16.dp)
        )

        Image(
            painter = painterResource(Res.drawable.close),
            contentDescription = null,
            modifier = Modifier.padding(LocalDimensionStyle.current.pagePadding).size(68.dp).clip(CircleShape).align(androidx.compose.ui.Alignment.TopEnd)
                .drawBackground(LocalColorStyle.current.iconBackground).clickable {
                    onClose.invoke()
                }.padding(16.dp)
        )
    }
}