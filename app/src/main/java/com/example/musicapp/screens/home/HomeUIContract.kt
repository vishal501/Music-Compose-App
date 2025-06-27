package com.example.musicapp.screens.home

import com.example.musicapp.UIEffect
import com.example.musicapp.UIEvent
import com.example.musicapp.UIState
import com.example.musicapp.model.AlbumOverviewModel
import com.example.musicapp.model.SongModel

class HomeUIContract {

    sealed class Event: UIEvent{
        data object LandingEvent: Event()
        data class SelectedSong(val songDetail: SongModel): Event()
    }

    data class State(
        val isLoading: Boolean = false,
        val topBillboardSong: List<SongModel> = listOf(),
        val songsForYou: List<SongModel> = listOf(),
        val trendingPlaylist: List<AlbumOverviewModel> = listOf(),
    ): UIState

    sealed class Effect: UIEffect{}

}