package com.example.musicapp.screens.songDetail

import androidx.compose.ui.graphics.Color
import com.example.musicapp.UIEffect
import com.example.musicapp.UIEvent
import com.example.musicapp.UIState
import com.example.musicapp.common.SongState

class SongDetailUIContract {

    sealed class Event: UIEvent{
        object FetchPlayingSongDetail: Event()
        object ListenSeek: Event()
        object Play: Event()
        object Pause: Event()
        object NextSong: Event()
        object StartAgainOrPreviousSong: Event()
        data class SeekTo(val ms: Long): Event()
        object Repeat: Event()
    }

    data class State(
        val songId: String = "",
        val songName: String = "Sample Name",
        val imageUrl: String = "",
        val artistName: String = "Artist Name",
        val songState: SongState = SongState.Pause,
        val durationInMs: Long = 0,
        val currentSeekPositionInMs: Long = 0,
        val songUrl: String = "",
        val bgColor: Color = Color.Red,
        val buffering: Boolean = true
    ): UIState

    sealed class Effect: UIEffect{}
}