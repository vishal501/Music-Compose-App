package com.example.musicapp.screens.home.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.PageSize.Fill.calculateMainAxisPageSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.core.sampleSongs
import com.example.musicapp.domain.toSongModel
import com.example.musicapp.model.SongModel
import com.example.musicapp.screens.songDetail.RemoteImage

@Composable
fun SongsForYou(
    songs: List<SongModel>,
    selectedSong: (SongModel) -> Unit
) {
    Text(
        text = stringResource(R.string.songs_for_you),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
    Spacer(modifier = Modifier.padding(10.dp))
    RecommendedSongs(
        modifier = Modifier,
        songs = songs,
        onClick = selectedSong)
}

@Composable
fun RecommendedSongs(modifier: Modifier,songs: List<SongModel>, onClick: (SongModel) -> Unit){
    val interactionSource = remember { MutableInteractionSource() }
    val rememberLazyState = rememberLazyListState()
    LazyRow(
        contentPadding = PaddingValues(20.dp),
        state = rememberLazyState,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.graphicsLayer {

        }
    ) {
        items(count = songs.size, key = {songs[it].id}){ index ->
            SongItem(songs[index], modifier = Modifier
                .width(150.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onClick(songs[index])
                }

            )
        }
    }
}

@Composable
fun SongItem(song: SongModel, modifier: Modifier){
    Column(modifier = modifier) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(MaterialTheme.shapes.medium.topEnd)
            )) {
            RemoteImage(
                url = song.imageUrl,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(MaterialTheme.shapes.medium.topEnd))
            )
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = song.name+" •"+song.artists.joinToString(separator = ",", transform = {it.name}),
            modifier = Modifier,
            maxLines = 1,
            style = MaterialTheme.typography.labelLarge
        )
    }
}



@Composable
@Preview(showBackground = true)
fun PreviewSongForYou(){
    Column {
        SongsForYou(songs = sampleSongs.map { it.toSongModel() }) {

        }
    }

}
