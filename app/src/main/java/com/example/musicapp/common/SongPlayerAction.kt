package com.example.musicapp.common

import androidx.compose.runtime.Stable

sealed class SongPlayerEvent{
    data class TogglePlayPause(val songState: SongState): SongPlayerEvent()
    data object NextSong: SongPlayerEvent()
    data object StartSong: SongPlayerEvent()
    data object PreviousSong: SongPlayerEvent()
    data class Liked(val songId: String): SongPlayerEvent()
    data class UnLiked(val songId: String): SongPlayerEvent()
}

enum class SongState{
    Play,
    Pause
}

//data class SeekTo(val ms: Float): SongPlayerAction()