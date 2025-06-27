package com.example.musicapp.screens.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicapp.screens.home.composables.SongsForYou
import com.example.musicapp.screens.home.composables.TopBillBoardSongs
import com.example.musicapp.screens.home.composables.TrendingAlbums
import com.example.musicapp.model.SongModel


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(modifier: Modifier,
               uiState: HomeUIContract.State,
               actionHandle: (HomeUIContract.Event) -> Unit,
               sharedTransitionScope: SharedTransitionScope,
               animatedContentScope: AnimatedContentScope,
               navigateToAlbumDetail: (String) -> Unit){
    LaunchedEffect(key1 = true) {
        actionHandle(HomeUIContract.Event.LandingEvent)
    }
    val scrollState = rememberScrollState()
    with(sharedTransitionScope){
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.verticalScroll(scrollState)) {
                TopBillBoardSongs(songs = uiState.topBillboardSong){ songModel ->
                    actionHandle(HomeUIContract.Event.SelectedSong(songModel))
                }
                Spacer(modifier = Modifier.padding(20.dp))
                TrendingAlbums(uiState.trendingPlaylist, sharedTransitionScope, animatedContentScope){ album ->
                    navigateToAlbumDetail(album.id)
                }
                //Spacer(modifier = Modifier.padding(20.dp))
                SongsForYou(songs = uiState.songsForYou){ songModel: SongModel ->
                    actionHandle(HomeUIContract.Event.SelectedSong(songModel))
                }
            }
        }
    }

}




//@OptIn(ExperimentalSharedTransitionApi::class)
//@Composable
//@Preview(showBackground = true)
//fun PreviewHomeScreen(){
//    SharedTransitionLayout {
//        HomeScreen(Modifier, HomeUIContract.State(), actionHandle = { (HomeUIContract.Event.LandingEvent) }, this@SharedTransitionLayout,
//            AnimatedContent(targetState = fadeOut()) {
//
//            }){}
//    }
//
//}
