package com.xetom.wancool.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import wancool.composeapp.generated.resources.Res
import wancool.composeapp.generated.resources.cloud_download
import wancool.composeapp.generated.resources.cloud

@Composable
fun OnlineImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
) {

    var state: AsyncImagePainter.State by remember {
        mutableStateOf(AsyncImagePainter.State.Empty)
    }

    Box(modifier = modifier) {

        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale,
            onState = {
                state = it
            }
        )

        when (state) {
            is AsyncImagePainter.State.Empty -> {

            }

            is AsyncImagePainter.State.Error -> {
                Image(
                    painter = painterResource(Res.drawable.cloud),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.2f).align(androidx.compose.ui.Alignment.Center).alpha(0.3f)
                )
            }

            is AsyncImagePainter.State.Loading -> {
                Image(
                    painter = painterResource(Res.drawable.cloud_download),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(0.2f).align(androidx.compose.ui.Alignment.Center).alpha(0.3f)
                )
            }

            is AsyncImagePainter.State.Success -> {

            }
        }

    }
}