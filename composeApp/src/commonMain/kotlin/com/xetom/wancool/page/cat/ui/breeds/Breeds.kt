package com.xetom.wancool.page.cat.ui.breeds

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xetom.wancool.page.home.business.CatBreedUIState
import com.xetom.wancool.resource.LocalColorStyle
import com.xetom.wancool.resource.LocalDimensionStyle
import com.xetom.wancool.widget.Gallery
import com.xetom.wancool.widget.OnlineImage
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import wancool.composeapp.generated.resources.Res
import wancool.composeapp.generated.resources.prefix_life_span
import wancool.composeapp.generated.resources.prefix_temperament

@Composable
fun CatBreeds(catBreeds: CatBreedUIState) {

    val catListState = rememberLazyListState()
    var catImage by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.fillMaxSize(), state = catListState, verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        itemsIndexed(items = catBreeds.breeds) { index, it ->

            Column(
                modifier = Modifier.fillMaxWidth().border(
                    width = 1.dp, color = LocalColorStyle.current.divider, shape = RoundedCornerShape(6.dp)
                ).padding(horizontal = LocalDimensionStyle.current.listItemPadding, vertical = 10.dp)
            ) {

                Row(modifier = Modifier.fillMaxWidth().height(128.dp)) {

                    OnlineImage(
                        url = it.image?.url.orEmpty(), modifier = Modifier.fillMaxHeight().aspectRatio(1f).clip(RoundedCornerShape(10.dp)).clickable {
                            it.image?.url?.let {
                                catImage = it
                            }
                        }, contentDescription = null, contentScale = ContentScale.Crop
                    )

                    Column(modifier = Modifier.padding(start = 20.dp).fillMaxHeight(), verticalArrangement = Arrangement.SpaceBetween) {

                        Row(verticalAlignment = Alignment.Bottom) {

                            Text(
                                text = it.name, color = LocalColorStyle.current.primary, fontSize = 28.sp, fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = it.origin, color = LocalColorStyle.current.description, fontSize = LocalDimensionStyle.current.cardDescription, modifier = Modifier.padding(start = 6.dp)
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
                    modifier = Modifier.padding(top = 10.dp), text = it.description, fontSize = LocalDimensionStyle.current.cardDescription, color = LocalColorStyle.current.description
                )
            }
        }
    }

    val showCat by remember {
        derivedStateOf {
            catImage.isNotEmpty()
        }
    }

    if (showCat) {
        Gallery(modifier = Modifier.fillMaxSize(), images = persistentListOf(catImage)) {
            catImage = ""
        }
    }
}