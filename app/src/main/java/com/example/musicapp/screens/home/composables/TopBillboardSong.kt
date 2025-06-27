package com.example.musicapp.screens.home.composables

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.room.util.joinIntoString
import com.example.musicapp.R
import com.example.musicapp.core.sampleSongs
import com.example.musicapp.domain.toSongModel
import com.example.musicapp.model.SongModel
import com.example.musicapp.screens.songDetail.RemoteImage
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopBillBoardSongs(songs: List<SongModel>,
                      selectedSong: (SongModel) -> Unit){
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = {
            songs.size.coerceAtMost(10)
        })
    Text(
        text = stringResource(R.string.billboard_hot_10),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
    Spacer(modifier = Modifier.padding(8.dp))

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .aspectRatio(1.5f),

        contentPadding = PaddingValues(start = 40.dp, end = 20.dp, top = 10.dp),
        pageSize =
        object : PageSize {
            override fun Density.calculateMainAxisPageSize(
                availableSpace: Int,
                pageSpacing: Int,
            ): Int {
                return ((availableSpace)/1.1f).roundToInt()
            }
        }
    ) { page ->
        val pageOffset = pagerState.calculateCurrentOffsetForPage(page)
        BillboardItem(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val offset = pagerState.calculateCurrentOffsetForPage(page)
                    val scale = lerp(1f, 0.9f, offset.absoluteValue)
                    scaleX = scale
                    scaleY = scale
                    alpha = lerp(1f, 0.5f, offset.absoluteValue)
                    val newY = lerp(0f, -20f,offset.absoluteValue*5 )
                    translationY = newY
                },
            songModel = songs[page],
            onClick = selectedSong
        )

    }
//    LaunchedEffect(key1 = true) {
//        pagerState.scrollToPage(page = 5)
//    }


}

@Composable
fun BillboardItem(modifier: Modifier, songModel: SongModel, onClick: (SongModel) -> Unit){
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(MaterialTheme.shapes.small.topEnd),
        onClick = { onClick(songModel) }
    ) {
        Box(contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()) {
            RemoteImage(
                url = songModel.imageUrl,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = songModel.name,
                style = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSurface, fontFamily = FontFamily.Cursive, fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .graphicsLayer {
                        translationX = -size.width/2 + size.height
                        rotationZ = -90f
                    }
                    .background(color = songModel.bgColor.copy(alpha = 0.3f))
                    .fillMaxWidth()
                    .align(Alignment.CenterStart)
            )
            Text(
                text = songModel.artists.joinToString(separator = ","){ it ->
                    it.name
                },
                style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center, fontFamily = FontFamily.Serif),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 50.dp)
                    .fillMaxWidth(),
                maxLines = 1
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

@Composable
@Preview(showBackground = true)
fun PreviewTopBillBoardSongs(){
    Column {
        TopBillBoardSongs(songs = sampleSongs.map { it.toSongModel() }) {

        }
    }

}