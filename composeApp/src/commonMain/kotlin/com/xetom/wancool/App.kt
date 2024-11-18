package com.xetom.wancool

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.xetom.wancool.api.getHttpClient
import com.xetom.wancool.ext.isOk;
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        HomePage()
    }
}

val COLUMNS = arrayOf(
    "Dog", "Cat", "Picture"
)

@Composable
fun HomePage(scope:CoroutineScope = rememberCoroutineScope()) {

    val columns by remember { mutableStateOf(COLUMNS) }
    var selectTab by remember { mutableStateOf(0) }

    var ip by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.Default) {
            val client = getHttpClient()
            val response = client.get("https://httpbin.org/ip")
            if (response.isOk()) {
                ip = response.bodyAsText()
            }
        }
    }

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
        Text(text = ip)
    }

}

//private fun io.ktor.client.statement.HttpResponse.isOk(): Boolean {
//    return this.status.value in 1..1000
//}
