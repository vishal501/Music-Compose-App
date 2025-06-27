package com.example.musicapp.screens.album

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

@Composable
fun AlbumDisk(modifier: Modifier, bgColor: Color) {
    val height = with(LocalDensity.current){
        ImageAspectRatio * LocalConfiguration.current.screenHeightDp.dp.toPx()
    }
    Box(modifier = Modifier.aspectRatio(0.88f), contentAlignment = Alignment.BottomEnd) {
        Canvas(
            modifier = modifier
                .zIndex(1f)
                .graphicsLayer {
                    shadowElevation = 100f
                }
        ) {
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val maxRadius = size.width / 2
            val minRadius = maxRadius / 3

            drawCircle(
                color = Color.Black,
                style = Fill,
                radius = maxRadius,
                center = Offset(centerX, centerY),
                blendMode = BlendMode.Luminosity
            )

            drawCircle(
                color = bgColor,
                style = Fill,
                radius = minRadius,
                center = Offset(centerX, centerY)
            )
            drawCircle(
                color = bgColor,
                style = Stroke(width = 2f),
                radius = minRadius,
                center = Offset(centerX, centerY)
            )

            drawCircle(
                color = Color.White,
                style = Fill,
                radius = 10f,
                center = Offset(centerX, centerY)
            )

        }
    }
}