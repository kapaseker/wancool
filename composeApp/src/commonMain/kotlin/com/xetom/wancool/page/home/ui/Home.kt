package com.xetom.wancool.page.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xetom.wancool.page.dog.ui.DogListScreen
import com.xetom.wancool.page.home.business.HomeViewModel
import com.xetom.wancool.style.LocalColorStyle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
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
    val breeds by home.breeds.collectAsState()

    Column {
        Row(modifier = Modifier.height(48.dp)) {
            if (columns.isNotEmpty()) {
                TabRow(
                    selectedTabIndex = selectTab,
                    modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(),
                    backgroundColor = Color.Transparent,
                    contentColor = Color.Black,
                ) {
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
            Box(
                modifier = Modifier.fillMaxHeight().aspectRatio(1f).clip(CircleShape).clickable {

            }.padding(14.dp)) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(Res.drawable.three_dots),
                    contentDescription = null,
                )
            }
        }

        var dogsList by remember { mutableStateOf<ImmutableList<String>>(persistentListOf<String>()) }
        var dogSelected by remember { mutableIntStateOf(-1) }

        when (selectTab) {
            0 -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(modifier = Modifier.weight(1f).fillMaxSize()) {
                            itemsIndexed(items = breeds.keys.toList()) { index, it ->
                                Column(modifier = Modifier.clickable {
                                    breeds[it]?.let { items ->
                                        dogSelected = index
                                        dogsList = items.toImmutableList()
                                    } ?: run {

                                    }
                                }) {
                                    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                                        Text(text = it, color = LocalColorStyle.current.primary, fontSize = 24.sp)
                                        if (dogSelected == index) {
                                            Spacer(Modifier.size(16.dp).clip(CircleShape).background(LocalColorStyle.current.primary).align(Alignment.CenterEnd))
                                        }
                                    }
                                    Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).padding(horizontal = 16.dp).background(LocalColorStyle.current.divider))
                                }
                            }
                        }
                        if (dogsList.isNotEmpty()) {
                            Spacer(modifier = Modifier.width(2.dp).fillMaxHeight().background(LocalColorStyle.current.divider))
                            DogListScreen(modifier = Modifier.weight(2f).fillMaxSize(), dogs = dogsList)
                        }
                    }
                }
            }
        }
    }
}
