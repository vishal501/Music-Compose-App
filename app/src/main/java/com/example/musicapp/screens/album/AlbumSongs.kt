package com.example.musicapp.screens.album

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.musicapp.model.SongModel
import com.example.musicapp.screens.songDetail.RemoteImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map

@Composable
fun AlbumSongs(
    listState: LazyListState,
    songs: List<SongModel>,
    modifier: Modifier,
    actionHandler: (AlbumUIContract.Event) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(20.dp),
        modifier = modifier,
        state = listState
    ) {
        items(count = songs.size, key = {songs[it].id}){ index ->
            val songModel = songs[index]
            AlbumSongItem(modifier = Modifier
                .clickable {
                    actionHandler(AlbumUIContract.Event.SelectedSong(songModel = songModel, position = index))
                }, songModel = songModel)
            Spacer(modifier = Modifier.padding(6.dp))
        }
    }
    snapshotFlow {
        listState.firstVisibleItemIndex
    }.map {
        Log.d("vchavhsdc", "AlbumSongs: ${it}")
    }
    LaunchedEffect(key1 = true) {
        delay(2000)

    }
}

@Composable
fun AlbumSongItem(modifier: Modifier,songModel: SongModel){
    Row(modifier = modifier) {
        RemoteImage(
            url = songModel.imageUrl,
            modifier = Modifier
                .size(50.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(MaterialTheme.shapes.small.topEnd)
                )
                .clip(shape = RoundedCornerShape(MaterialTheme.shapes.small.topEnd))

        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = songModel.name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth(0.8f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            //Text(text = songModel.artists.toString(), style = MaterialTheme.typography.labelMedium)
        }

    }
}