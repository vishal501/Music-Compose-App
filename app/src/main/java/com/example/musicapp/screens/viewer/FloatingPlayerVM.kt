package com.example.musicapp.screens.viewer

import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import com.example.musicapp.BaseViewModel
import com.example.musicapp.playerHandler.PlayerControllerFactory
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FloatingPlayerVM @Inject constructor(): BaseViewModel<FloatingPlayerUIContract.Event, FloatingPlayerUIContract.State, FloatingPlayerUIContract.Effect>() {

    private val playerController by lazy {
        PlayerControllerFactory().getPlayerController()
    }

    override fun createInitialState(): FloatingPlayerUIContract.State {
        return FloatingPlayerUIContract.State()
    }

    override fun handleEvent(event: FloatingPlayerUIContract.Event) {
        when(event){
            is FloatingPlayerUIContract.Event.FetchSongDetail -> {
                getCurrentMediaDetail()
                playerStateObserver()
                observeSongChange()
//                playerController.getSongPlayStateFlow().onEach {
//                    setState { copy(songState = it) }
//                }.launchIn(this.viewModelScope)
            }
            is FloatingPlayerUIContract.Event.ListenSongSeek -> {
                observeSeek()
            }
            is FloatingPlayerUIContract.Event.Play -> {
                playerController.play(seekPos = state.value.currentSeekPosition)
            }
            is FloatingPlayerUIContract.Event.Pause -> {
                playerController.pause()
            }
        }

    }

    private fun observeSeek() {
        playerController.seekFlow().onEach {
            setState { copy(currentSeekPosition = it.coerceAtLeast(0)) }
        }.launchIn(viewModelScope)
    }

    private fun getCurrentPlayingSongDuration() {
        launchInViewModelScope {
            val songLength = playerController.getSongDuration()
            setState { copy(songDuration = songLength) }
        }
    }

    private fun observeSongChange() {
        playerController.getTrackChange().distinctUntilChanged().onEach {
            val (index, track) = it
            getCurrentMediaDetail()
            getCurrentPlayingSongDuration()
        }.launchIn(viewModelScope)
    }

    private fun playerStateObserver() {
        playerController.playerStateFlow().onEach { playbackState ->
            when (playbackState) {
                Player.STATE_IDLE -> {
                }
                Player.STATE_BUFFERING -> {
                    setState { copy(buffering = true) }
                }
                Player.STATE_READY -> {
                    setState { copy(buffering = false) }
                }
                Player.STATE_ENDED -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getCurrentMediaDetail() {
        val mediaDetail = playerController.getCurrentPlayingMediaDetail()
        setState {
            copy(
                songName = mediaDetail.name,
                songState = state.value.songState,
                artistName = mediaDetail.artistName,
                songId = mediaDetail.songId,
                imageUrl = mediaDetail.imageUrl
            )
        }
        getCurrentPlayingSongDuration()
    }
}