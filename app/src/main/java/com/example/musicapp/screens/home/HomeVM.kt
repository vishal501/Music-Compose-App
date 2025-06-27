package com.example.musicapp.screens.home

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.example.musicapp.BaseViewModel
import com.example.musicapp.playerHandler.MediaDetail
import com.example.musicapp.playerHandler.PlayerControllerFactory
import com.example.musicapp.domain.AlbumRepository
import com.example.musicapp.domain.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(
    private val songRepository: SongRepository,
    private val albumRepository: AlbumRepository
): BaseViewModel<HomeUIContract.Event, HomeUIContract.State, HomeUIContract.Effect>() {

    override fun createInitialState(): HomeUIContract.State {
        return HomeUIContract.State()
    }

    private val mediaController by lazy { PlayerControllerFactory().getPlayerController() }

    override fun handleEvent(event: HomeUIContract.Event) {
        when(event){
            is HomeUIContract.Event.LandingEvent -> {
                launchInViewModelScope {
                    val list = songRepository.getAllSongs()
                    val albums = albumRepository.getAlbums()
                    Log.d("ncjbsjck", "handleEvent: $list")
                    setState { copy(songsForYou = list, topBillboardSong = list, trendingPlaylist = albums) }
                }
            }
            is HomeUIContract.Event.SelectedSong -> {
                mediaController.addMedia(
                    list = listOf(
                        MediaDetail(
                    songId = event.songDetail.id,
                    uri = event.songDetail.uri,
                    name = event.songDetail.name,
                    artistName = event.songDetail.artists.joinToString(separator = ",") { it.name },
                    imageUrl = event.songDetail.imageUrl, bgColor = Color.Blue
                        )
                    )
                )
                mediaController.play(0, 0)
            }
        }

    }
}