package com.example.musicapp.screens.viewer

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicapp.common.SongState
import com.example.musicapp.screens.songDetail.PlayAndPause
import com.example.musicapp.screens.songDetail.RemoteImage

@Composable
fun FloatingPlayer(
    modifier: Modifier,
    uiState: FloatingPlayerUIContract.State,
    actionHandler: (FloatingPlayerUIContract.Event) -> Unit,
    playingSongId: String,
    onClick: (id: String) -> Unit){

    LaunchedEffect(playingSongId) {
        actionHandler(FloatingPlayerUIContract.Event.FetchSongDetail(playingSongId))
        actionHandler(FloatingPlayerUIContract.Event.ListenSongSeek)
    }
    val interactionSource = remember { MutableInteractionSource() }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(MaterialTheme.shapes.extraSmall.topEnd))
            .background(
                shape = RoundedCornerShape(MaterialTheme.shapes.extraSmall.topEnd),
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick(uiState.songId)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 7.dp)) {
            RemoteImage(
                url = uiState.imageUrl,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(MaterialTheme.shapes.extraSmall.topEnd))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(MaterialTheme.shapes.extraSmall.topEnd)
                    ),
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = uiState.songName,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier,
                    color =  MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = uiState.artistName,
                    style = MaterialTheme.typography.labelSmall,
                    color =  MaterialTheme.colorScheme.primary
                )
            }
            PlayAndPause(
                songState = uiState.songState,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .size(30.dp),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary)
            ){
                when (uiState.songState) {
                    SongState.Pause -> {
                        actionHandler(FloatingPlayerUIContract.Event.Play)
                    }

                    SongState.Play -> {
                        actionHandler(FloatingPlayerUIContract.Event.Pause)
                    }
                }
            }
        }


        if(uiState.buffering && uiState.songState == SongState.Play){
            Box(modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
                .background(brush = shimmerBrush(showShimmer = true))
            ) {

            }
        }else{
            LinearProgressIndicator(
                progress = { uiState.currentSeekPosition.toFloat()/uiState.songDuration },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .height(4.dp),
                trackColor = MaterialTheme.colorScheme.inversePrimary,
                strokeCap = StrokeCap.Square,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun shimmerBrush(showShimmer: Boolean = true,targetValue:Float = 1000f): Brush {
    return if (showShimmer) {
        val shimmerColors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
        )

        val transition = rememberInfiniteTransition()
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = targetValue,
            animationSpec = infiniteRepeatable(
                animation = tween(1000), repeatMode = RepeatMode.Restart
            )
        )
        Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero,
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    } else {
        Brush.linearGradient(
            colors = listOf(Color.Transparent,Color.Transparent),
            start = Offset.Zero,
            end = Offset.Zero
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewSongViewer(){
    Box {
        FloatingPlayer(Modifier, FloatingPlayerUIContract.State(songName = "Sample", artistName = "Sample"),{}, ""){ songId ->

        }
    }
}