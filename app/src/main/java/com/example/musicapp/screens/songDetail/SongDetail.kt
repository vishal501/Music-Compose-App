package com.example.musicapp.screens.songDetail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import com.example.musicapp.R
import com.example.musicapp.common.SongState
import kotlin.math.roundToLong

@Composable
fun SongDetailScreen(modifier: Modifier,
                     actionHandler: (SongDetailUIContract.Event) -> Unit,
                     uiState: SongDetailUIContract.State,
){
    val scrollState = rememberScrollState()
    LaunchedEffect(true) {
        actionHandler(SongDetailUIContract.Event.FetchPlayingSongDetail)
        actionHandler(SongDetailUIContract.Event.ListenSeek)
    }
    Column(
        modifier = modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
    ) {
        SongImageWithController(imageUrl = uiState.imageUrl, songId = uiState.songId, bgColor = uiState.bgColor) {
            PlayerDashBoard(
                modifier = Modifier
                    .imePadding(),
                uiState = uiState,
                action = actionHandler
            )
        }
//        ArtistCard(
//            modifier = Modifier
//                .padding(20.dp)
//                .fillMaxWidth()
//                .clip(shape = RoundedCornerShape(20.dp))
//                .background(color = MaterialTheme.colorScheme.inversePrimary),
//        )
    }
}

@Composable
fun SongImageWithController(
    imageUrl: String,
    songId: String,
    bgColor: Color,
    content: @Composable () -> Unit,

) {
    val screenHeight = LocalConfiguration.current.screenHeightDp
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.radialGradient(
                    listOf(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                    ),
                )
            )
    ) {
        Column {
            RemoteImage(
                url = imageUrl,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 50.dp)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(MaterialTheme.shapes.medium.topEnd),
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                    )
                    .clip(shape = RoundedCornerShape(MaterialTheme.shapes.medium.topEnd))
                    .aspectRatio(1f)
            )
            content()
        }

        ActionButton(
            painterId = R.drawable.vertical_three_dot_icon,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp)
                .size(20.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewSongDetail(){
    SongDetailScreen(modifier = Modifier, {(SongDetailUIContract.Event.Play) }, SongDetailUIContract.State())
}

@Composable
fun PlayerDashBoard(
    modifier: Modifier,
    uiState: SongDetailUIContract.State,
    action: (SongDetailUIContract.Event) -> Unit,
){
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(20.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)) {
                Text(
                    text = uiState.songName,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = uiState.artistName,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
//            UnLikeButton(modifier = Modifier.align(Alignment.CenterVertically))
//            LikeButton(modifier =  Modifier.align(Alignment.CenterVertically))
        }
        SongSeekbar(
            seekValue = uiState.currentSeekPositionInMs,
            totalValue = uiState.durationInMs,
            modifier = Modifier.fillMaxWidth(), seekTo = { action(SongDetailUIContract.Event.SeekTo(it)) }
            ){

        }
        PlayerController(modifier = Modifier.fillMaxWidth(), songState = uiState.songState, action)
    }
}

@Composable
fun RemoteImage(url:  String,modifier: Modifier, placeHolder: Int? = null, contentScale: ContentScale =  ContentScale.FillBounds){
    val imageLoader = LocalContext.current.imageLoader
    AsyncImage(
        model = url,
        contentDescription = null,
        imageLoader = imageLoader,
        modifier = modifier,
        placeholder = placeHolder?.let { painterResource(id = it) },
        contentScale = contentScale
    )
}

@Composable
fun LikeButton(modifier: Modifier){
    Image(painter = painterResource(id = R.drawable.thumbs_up_filled),
        contentDescription = "like_icon",
        modifier = modifier.size(25.dp)
    )
}

@Composable
fun UnLikeButton(modifier: Modifier){
    Image(painter = painterResource(id = R.drawable.thumbs_down_outline),
        contentDescription = "like_icon",
        modifier = modifier.size(25.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongSeekbar(modifier: Modifier, seekValue: Long, totalValue: Long, seekTo: (Long) -> Unit, onComplete: () -> Unit){
    val sliderPosition = totalValue*seekValue.toFloat()/totalValue
    val thumbColor = MaterialTheme.colorScheme.primary
    Slider(
        value = sliderPosition,
        onValueChange = {
            seekTo(it.roundToLong())
        },
        modifier = modifier,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.primary,
            activeTrackColor = MaterialTheme.colorScheme.secondary,
        ),
        valueRange = 0f..totalValue.toFloat(),
        onValueChangeFinished = onComplete,
    )
    Row {
        Text(text = seekValue.convertMsInTime(), modifier = Modifier.weight(1f))
        Text(text = totalValue.convertMsInTime())
    }
}

@Composable
fun PlayerController(
    modifier: Modifier,
    songState: SongState,
    action: (SongDetailUIContract.Event) -> Unit,
){
    Box(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)) {
            ActionButton(painterId = R.drawable.repeat_icon, modifier = Modifier
                .size(20.dp)
                .pointerInput(Unit) {
                    detectTapGestures { action(SongDetailUIContract.Event.Repeat) }
                })
            ActionButton(painterId = R.drawable.step_backward_icon, modifier = Modifier
                .size(30.dp)
                .weight(1f)
                .pointerInput(Unit) {
                    detectTapGestures { action(SongDetailUIContract.Event.StartAgainOrPreviousSong) }
                }
            )
            PlayAndPause(
                songState = songState,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(60.dp),
                action = {
//                    when (uiState.songState) {
//                        SongState.Pause -> action(SongDetailUIContract.Event.Play)
//                        SongState.Play -> action(SongDetailUIContract.Event.Pause)
//                    }
                }
            )
            ActionButton(painterId = R.drawable.step_forward_icon, modifier = Modifier
                .size(30.dp)
                .weight(1f)
                .pointerInput(Unit) {
                    detectTapGestures { action(SongDetailUIContract.Event.NextSong) }
                }
            )
            ActionButton(painterId = R.drawable.shuffle_icon, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun PlayAndPause(modifier: Modifier, songState: SongState, colorFilter: ColorFilter?= null, action: () -> Unit){
    val painterId = remember(songState) {
        when(songState){
            SongState.Play -> R.drawable.pause_round_icon
            SongState.Pause -> R.drawable.play_round_icon
        }
    }
    Log.d("dbhbajdh", "PlayAndPause: $songState")
    Image(
        painter = painterResource(id = painterId),
        contentDescription = null,
        modifier = modifier.clickable {
            action()
        },
        colorFilter = colorFilter
    )
}

@Composable
fun ActionButton(painterId: Int, modifier: Modifier, colorFilter: ColorFilter? = ColorFilter.tint(color = MaterialTheme.colorScheme.primary),){
    Image(
        painter = painterResource(id = painterId),
        contentDescription = null,
        modifier = modifier,
        colorFilter = colorFilter
    )
}
@Composable
fun ArtistCard(modifier: Modifier){
    Column(modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        RemoteImage(url = "",
            modifier = Modifier
                .aspectRatio(16f / 9f)
                .background(color = Color.Blue)
        )
        Text(
            text = "bcdjadj jndja bdhj \n gdhahdbah \n dhygahd\n dygahn gdygau\n ygdyag\n gdyga\n",
            maxLines = 5
        )
    }
}

@SuppressLint("DefaultLocale")
fun Long.convertMsInTime(): String{
    val minutes: Long = (this / 1000) / 60
    val seconds: Long = (this / 1000) % 60
    return String.format("%02d:%02d", minutes, seconds)

}






