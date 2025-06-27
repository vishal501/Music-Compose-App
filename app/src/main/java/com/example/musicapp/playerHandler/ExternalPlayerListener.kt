package com.example.musicapp.playerHandler

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import com.example.musicapp.common.SongState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class ExternalPlayerListener: Player.Listener {

    private val _songPlayerState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val songPlayPauseFlow = _songPlayerState.asStateFlow().map {
        if(it){
            SongState.Play
        }else{
            SongState.Pause
        }
    }.distinctUntilChanged()

    private val _playerState: MutableStateFlow<Int> = MutableStateFlow(Player.STATE_IDLE)
    val playerState = _playerState.asStateFlow()

    private val _trackChange: MutableSharedFlow<Tracks> = MutableSharedFlow()
    val trackChange = _trackChange.asSharedFlow()

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
        runBlocking {
            _songPlayerState.emit(playWhenReady)
        }
        Log.d("vdhwhdlwjdcw", "listener song controller$playWhenReady")
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
    }



    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)

        // Log.d("gydgydgu", "onMediaItemTransition: ${mediaItem!!.mediaId} and ${mediaController.currentMediaItemIndex}")
    }

    override fun onTracksChanged(tracks: Tracks) {
        super.onTracksChanged(tracks)
        runBlocking {
            _trackChange.emit(tracks)
        }
    }


    override fun onPlaybackStateChanged(playbackState: Int) {
        runBlocking { _playerState.emit(playbackState) }
        when (playbackState) {

            Player.STATE_IDLE -> {
                Log.d("bhbcdah", "onSeekBackIncrementChanged:Idle")
            }
            Player.STATE_BUFFERING -> {
                Log.d("bhbcdah", "onSeekBackIncrementChanged: Buffering")
            }
            Player.STATE_READY -> {
                Log.d("bhbcdah", "onSeekBackIncrementChanged: REady")
            }
            Player.STATE_ENDED -> {
                Log.d("bhbcdah", "onSeekBackIncrementChanged: ended")
            }
        }
    }
    override fun onSeekBackIncrementChanged(seekBackIncrementMs: Long) {
        super.onSeekBackIncrementChanged(seekBackIncrementMs)
        Log.d("bhbcdah", "onSeekBackIncrementChanged: $seekBackIncrementMs")
    }

    override fun onPlaylistMetadataChanged(mediaMetadata: MediaMetadata) {
        super.onPlaylistMetadataChanged(mediaMetadata)
        Log.d("ncjndck", "onPlaylistMetadataChanged: $mediaMetadata")
    }

}