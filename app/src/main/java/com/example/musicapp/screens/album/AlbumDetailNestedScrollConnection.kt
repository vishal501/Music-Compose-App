package com.example.musicapp.screens.album

import androidx.compose.animation.fadeOut
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import kotlin.math.absoluteValue
import kotlin.math.sign

class  AlbumDetailNestedScrollConnection(
    private val imageHeight: Float
): NestedScrollConnection {

    var imageOffset by mutableFloatStateOf(0f)
        private set

    val showAppBarState by
        derivedStateOf { collapseFraction.absoluteValue in 0.9f..1f  }

    val collapseFraction by derivedStateOf {
        (imageOffset/(imageHeight)).coerceIn(-1f,1f)
    }


    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        val availableY = available.y
        val directionUp = sign(availableY) == -1.0f
        val totalScroll = imageOffset + availableY
        imageOffset = (totalScroll).coerceIn(-imageHeight, 0f)
        val consume: Float
        if(totalScroll.absoluteValue > imageOffset.absoluteValue ){
            consume = 0f
        }else{
            consume = availableY
        }
        return available.copy(y = consume)
    }
}