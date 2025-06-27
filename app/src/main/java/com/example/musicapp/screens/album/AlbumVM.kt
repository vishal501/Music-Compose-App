package com.example.musicapp.screens.album

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.musicapp.BaseViewModel
import com.example.musicapp.common.SongState
import com.example.musicapp.domain.AlbumRepository
import com.example.musicapp.domain.ArtistRepository
import com.example.musicapp.playerHandler.MediaDetail
import com.example.musicapp.playerHandler.PlayerControllerFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AlbumVM @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository
): BaseViewModel<AlbumUIContract.Event,
        AlbumUIContract.State,
        AlbumUIContract.Effect>() {

    private val albumId = savedStateHandle.get<String>("albumId")!!
    private val mediaController by lazy { PlayerControllerFactory().getPlayerController() }

    override fun createInitialState(): AlbumUIContract.State {
        return AlbumUIContract.State()
    }

    override fun handleEvent(event: AlbumUIContract.Event) {
        when(event){
            is AlbumUIContract.Event.SelectedSong -> {
                val playlist = state.value.albumDetail.songs.map { MediaDetail(
                    songId = it.id,
                    uri = it.uri,
                    name = it.name,
                    artistName = state.value.artists.joinToString(separator = ",") { it.name },
                    imageUrl = it.imageUrl,
                    bgColor = it.bgColor
                ) }
                mediaController.addMedia(list = playlist)
                mediaController.play(event.position, 0)
            }
            is AlbumUIContract.Event.FetchAlbumDetail -> {
                Log.d("vdhavk", "handleEvent: $albumId")
                launchInViewModelScope {
                    val data = albumRepository.getAlbumDetail(albumId)
                    val artists = artistRepository.getArtistDetail(data.artistIds)
                    setState { copy(
                        albumDetail = data,
                        artists = artists
                    ) }
                }
//                mediaController.getTrackChange().distinctUntilChanged().onEach {
//                    val (index, track) = it
//                    getCurrentMediaDetail()
//                    launchInViewModelScope {
//                        val duration = mediaController.getSongDuration()
//                        setState { copy(durationInMs = duration) }
//                    }
//                }.launchIn(viewModelScope)
            }
            is AlbumUIContract.Event.PlayAlbum -> {
                val playlist = state.value.albumDetail.songs.map { MediaDetail(
                    songId = it.id,
                    uri = it.uri,
                    name = it.name,
                    artistName = it.artists.toString(),
                    imageUrl = it.imageUrl,
                    bgColor = it.bgColor
                ) }
                mediaController.addMedia(list = playlist)
                mediaController.play(0, 0)
            }
        }

    }

    private fun getCurrentMediaDetail() {
//        val mediaDetail = mediaController.getCurrentPlayingMediaDetail()
//        setState { copy(
//            songName = mediaDetail.name,
//            songId = mediaDetail.songId,
//            imageUrl = mediaDetail.imageUrl,
//            songState = if(songMediaController.isSongPlaying()) SongState.Play else SongState.Pause,
//            bgColor = mediaDetail.bgColor,
//            artistName = mediaDetail.artistName
//        ) }
    }
}