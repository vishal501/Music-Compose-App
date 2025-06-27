package com.example.musicapp.screens.songDetail

import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import com.example.musicapp.BaseViewModel
import com.example.musicapp.playerHandler.PlayerController
import com.example.musicapp.playerHandler.PlayerControllerFactory
import com.example.musicapp.common.SongState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SongDetailVM @Inject constructor(): BaseViewModel<
        SongDetailUIContract.Event,
        SongDetailUIContract.State,
        SongDetailUIContract.Effect>() {


    private val songMediaController: PlayerController by lazy { PlayerControllerFactory().getPlayerController() }

    override fun createInitialState(): SongDetailUIContract.State {
        return SongDetailUIContract.State()
    }

    override fun handleEvent(event: SongDetailUIContract.Event) {
        when(event){
            is SongDetailUIContract.Event.FetchPlayingSongDetail -> {
                getCurrentMediaDetail()
//                playerStateObserver()
//                observeSongChange()
//                songMediaController.getSongPlayStateFlow().onEach {
//                    setState { copy(songState = it) }
//                }.launchIn(this.viewModelScope)
            }
            is SongDetailUIContract.Event.ListenSeek -> {
                songMediaController.seekFlow().onEach {
                    setState { copy(currentSeekPositionInMs = it) }
                }.launchIn(viewModelScope)
            }
            is SongDetailUIContract.Event.Play -> {
                songMediaController.play(seekPos = state.value.currentSeekPositionInMs)
                setState { copy(songState = SongState.Play) }
            }
            is SongDetailUIContract.Event.Pause -> {
                songMediaController.pause()
                setState { copy(songState = SongState.Pause) }
            }
            is SongDetailUIContract.Event.SeekTo -> {
                songMediaController.seekTo(event.ms)
                setState { copy(currentSeekPositionInMs = event.ms) }
            }
            is SongDetailUIContract.Event.NextSong -> {
                songMediaController.moveToNextSong()
//                if(state.value.songState == SongState.Play){
//                    songMediaController.play()
//                }
            }
            is SongDetailUIContract.Event.StartAgainOrPreviousSong -> {
                songMediaController.moveToPreviousSong()
            }
            is SongDetailUIContract.Event.Repeat -> {

            }
        }
    }

    private fun getSongDuration() {
        launchInViewModelScope {
            val duration = songMediaController.getSongDuration()
            setState { copy(durationInMs = duration) }
        }
    }

    private fun observeSongChange() {
        songMediaController.getTrackChange().distinctUntilChanged().onEach {
            val (index, track) = it
            getCurrentMediaDetail()
        }.launchIn(viewModelScope)
        getSongDuration()
    }

    private fun getCurrentMediaDetail() {
        val mediaDetail = songMediaController.getCurrentPlayingMediaDetail()
        setState { copy(
            songName = mediaDetail.name,
            songId = mediaDetail.songId,
            imageUrl = mediaDetail.imageUrl,
            songState = if(songMediaController.isSongPlaying()) SongState.Play else SongState.Pause,
            bgColor = mediaDetail.bgColor,
            artistName = mediaDetail.artistName
        ) }
        getSongDuration()
    }

    private fun playerStateObserver() {
        songMediaController.playerStateFlow().onEach { playbackState ->
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
}


