package com.example.musicapp.screens.viewer

import com.example.musicapp.UIEffect
import com.example.musicapp.UIEvent
import com.example.musicapp.UIState
import com.example.musicapp.common.SongState

class FloatingPlayerUIContract {

    sealed class Event: UIEvent{
        data class FetchSongDetail(val id: String): Event()
        data object ListenSongSeek: Event()
        data object Pause: Event()
        data object Play: Event()
    }


    sealed class Effect: UIEffect

    data class State(
        val songId: String = "",
        val songName: String =  "",
        val artistName: String = "",
        val imageUrl: String = "",
        val songState: SongState = SongState.Play,
        val currentSeekPosition: Long = 0,
        val songDuration: Long = 0,
        val buffering: Boolean = true
    ): UIState
}