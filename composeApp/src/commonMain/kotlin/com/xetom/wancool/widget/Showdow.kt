package com.xetom.wancool.widget

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skia.FilterBlurMode
import org.jetbrains.skia.MaskFilter

fun Modifier.blurShadow(
    offsetX: Float = 0f,
    offsetY: Float = 0f,
    blurRadius: Float = 0f,
    cornerRadius: Float = 0f,
    color: Color,
) = this.drawWithCache {

    val paint = Paint()
    val frameworkPaint = paint.asFrameworkPaint()
    frameworkPaint.maskFilter = MaskFilter.makeBlur(FilterBlurMode.NORMAL, blurRadius, false)
    frameworkPaint.color = color.toArgb()
    val rightPixel = size.width + offsetX
    val bottomPixel = size.height + offsetY

    onDrawBehind {
        this.drawIntoCanvas {
            if (cornerRadius > 0f) {
                it.drawRoundRect(
                    left = offsetX,
                    top = offsetY,
                    right = rightPixel,
                    bottom = bottomPixel,
                    radiusX = cornerRadius,
                    radiusY = cornerRadius,
                    paint = paint,
                )
            } else {
                it.drawRect(
                    left = offsetX,
                    top = offsetY,
                    right = rightPixel,
                    bottom = bottomPixel,
                    paint = paint,
                )
            }
        }
    }
}