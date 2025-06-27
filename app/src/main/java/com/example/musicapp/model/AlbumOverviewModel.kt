package com.example.musicapp.model

data class AlbumOverviewModel(
    val id: String,
    val title: String,
    val name: String,
    val imageUrl: String,
    val artistName: String,
    val songs: Int = 0,
    val releaseDate: String,
    val bgColor: String = ""
)
