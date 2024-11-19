package com.xetom.wancool.page.dog.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xetom.wancool.style.LocalColorStyle
import kotlinx.collections.immutable.ImmutableList

@Composable
fun DogListScreen(modifier: Modifier = Modifier, dogs: ImmutableList<String>) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),

            ) {
            items(items = dogs) {
                Column(modifier = Modifier.clickable {

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