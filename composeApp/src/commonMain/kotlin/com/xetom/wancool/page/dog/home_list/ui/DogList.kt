package com.xetom.wancool.page.dog.home_list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xetom.wancool.resource.LocalColorStyle
import kotlinx.collections.immutable.ImmutableList

@Composable
fun DogListScreen(modifier: Modifier = Modifier, dogs: ImmutableList<String>, onClick: (Int) -> Unit) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(items = dogs) { index, it ->
                Column(modifier = Modifier.clickable {
                    onClick.invoke(index)
                }) {
                    Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                        Text(text = it, color = LocalColorStyle.current.primary, fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.fillMaxWidth().height(1.dp).padding(horizontal = 12.dp).background(LocalColorStyle.current.divider))
                }
            }
        }
    }
}