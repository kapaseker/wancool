package com.xetom.wancool.page.library.ui.library

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LibraryPage(
    scope: CoroutineScope = rememberCoroutineScope()
) {

    val transition = rememberInfiniteTransition("rotation")

    val rotation by transition.animateFloat(
        0f, 360f, animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ), label = "r"
    )

    var matrix by remember { mutableStateOf(Matrix(), neverEqualPolicy()) }



    LaunchedEffect(Unit) {
        snapshotFlow { rotation }.collectLatest {
            val ma = Matrix()
            ma.rotateY(it)
            ma.rotateX(60f)
            matrix = Matrix(ma.values)
        }
//        matrix.rotateY(20f)
    }

    Box(modifier = Modifier.fillMaxWidth(0.5f).fillMaxHeight(0.8f).border(2.dp, Color.Red)) {
        Column(modifier = Modifier.drawBehind {
            withTransform({
                println("$center")
                this.transform(matrix)
            }) {
                drawRect(Color.Red)
            }
        }.fillMaxSize(0.9f).align(Alignment.Center)) {
            Text("this is rotate good")
        }
    }
}
