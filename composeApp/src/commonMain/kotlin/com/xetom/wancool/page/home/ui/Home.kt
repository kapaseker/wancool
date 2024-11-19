package com.xetom.wancool.page.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.xetom.wancool.api.IpApi
import com.xetom.wancool.http.getHttpClient
import com.xetom.wancool.ext.isOk;
import com.xetom.wancool.page.home.business.HomeViewModel
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel


val COLUMNS = arrayOf(
    "Dog", "Cat", "Picture"
)

@Composable
fun HomePage(
    scope: CoroutineScope = rememberCoroutineScope(),
    home: HomeViewModel = viewModel { HomeViewModel() }
) {

    val columns by remember { mutableStateOf(COLUMNS) }
    var selectTab by remember { mutableStateOf(0) }

    val ip by home.ip.collectAsState()
    val breeds by home.breeds.collectAsState()

    Column {
        TabRow(
            selectedTabIndex = selectTab
        ) {
            columns.withIndex().forEach {
                Tab(selectTab == it.index, text = {
                    Text(text = it.value, color = Color.White)
                }, onClick = {
                    selectTab = it.index
                })
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = breeds.keys.toList()) {
                    Text(text = it, color = Color.Black)
                }
            }
        }
    }
}
