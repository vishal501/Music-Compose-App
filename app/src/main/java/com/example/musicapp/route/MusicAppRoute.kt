package com.example.musicapp.route

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.musicapp.screens.album.AlbumDetailScreen
import com.example.musicapp.screens.album.AlbumUIContract
import com.example.musicapp.screens.album.AlbumVM
import com.example.musicapp.screens.home.HomeScreen
import com.example.musicapp.screens.home.HomeUIContract
import com.example.musicapp.screens.home.HomeVM
import com.example.musicapp.screens.songDetail.SongDetailScreen
import com.example.musicapp.screens.songDetail.SongDetailVM

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MusicAppRoute(modifier: Modifier,
                  navController: NavHostController,
                  songSelected: (songId: String) -> Unit) {
    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = MusicAppScreen.Home.name){
            composable(route = MusicAppScreen.Home.name){
                val vm: HomeVM = hiltViewModel()
                HomeScreen(modifier = modifier
                    .fillMaxSize()
                    .background(color = Color.Blue),
                    uiState = vm.state.collectAsState().value,
                    actionHandle = {
                        if(it is HomeUIContract.Event.SelectedSong){
                            songSelected(it.songDetail.id)
                        }
                        vm.handleEvent(it)
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                ){ albumId ->
                    navController.navigate("${MusicAppScreen.Album.name}/${albumId}")
                }
            }

            composable(
                route = MusicAppScreen.Album.name+"/{albumId}",
                arguments = listOf(navArgument("albumId") { type = NavType.StringType})
            ){ navBackStackEntry ->
                val vm: AlbumVM = hiltViewModel()

                with(this@SharedTransitionLayout){
                    AlbumDetailScreen(
                        modifier = Modifier,
                        animatedContentScope = this@composable,
                        uiState =  vm.state.collectAsState().value,
                        actionHandler = { event ->
                            if(event is AlbumUIContract.Event.SelectedSong){
                                songSelected(event.songModel.id)
                            }else if (event is AlbumUIContract.Event.PlayAlbum){
                                songSelected(vm.state.value.albumDetail.songs[0].id)
                            }
                            vm.handleEvent(event)
                        }
                    )
                }
            }


//            composable(route = MusicAppScreen.Song_Detail.name){
//
//            }
        }
    }


}

enum class MusicAppScreen{
    Home,
    Album,
    //Song_Detail
}