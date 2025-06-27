package com.example.musicapp.screens.album

import com.example.musicapp.UIEffect
import com.example.musicapp.UIEvent
import com.example.musicapp.UIState
import com.example.musicapp.domain.AlbumDetail
import com.example.musicapp.model.ArtistModel
import com.example.musicapp.model.SongModel

class AlbumUIContract {

    sealed class Event: UIEvent{
        data object FetchAlbumDetail: Event()
        data object PlayAlbum: Event()
        data class SelectedSong(val songModel: SongModel, val position: Int): Event()
    }

    sealed class Effect: UIEffect

    data class State(
        val albumDetail: AlbumDetail = AlbumDetail(
            title = "",
            imageUrl = "",
            artistIds = listOf(),
            about = "",
            albumLength = "",
            songs = listOf(),
            id = "",
            bgColor = "#FFFFFF"
        ),
        val artists: List<ArtistModel> = listOf()
    ): UIState
}