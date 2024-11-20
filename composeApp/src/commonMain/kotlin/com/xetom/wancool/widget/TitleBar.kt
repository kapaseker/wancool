package com.xetom.wancool.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xetom.wancool.resource.LocalColorStyle
import com.xetom.wancool.resource.LocalDimensionStyle
import org.jetbrains.compose.resources.painterResource
import wancool.composeapp.generated.resources.Res
import wancool.composeapp.generated.resources.arrow_left

@Composable
fun TitleBar(
    modifier: Modifier = Modifier, title: String, onBack: (() -> Unit)? = null
) {

    val onBackState by rememberUpdatedState(onBack)

    Box(
        modifier = modifier.fillMaxWidth().height(LocalDimensionStyle.current.titleBar).titleBarShadow().drawBackground(LocalColorStyle.current.background)
    ) {
        Text(
            text = title, fontSize = 28.sp, color = LocalColorStyle.current.primary, modifier = Modifier.align(Alignment.Center)
        )

        onBackState?.let { back ->
            Image(
                modifier = Modifier.fillMaxHeight().aspectRatio(1f).clip(CircleShape).align(Alignment.CenterStart).clickable(onClick = back).padding(10.dp),
                contentDescription = null,
                painter = painterResource(Res.drawable.arrow_left)
            )
        }
    }
}