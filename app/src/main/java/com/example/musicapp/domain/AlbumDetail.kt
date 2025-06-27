package com.example.musicapp.domain

import com.example.musicapp.model.SongModel

data class AlbumDetail(
    val id: String,
    val title: String,
    val imageUrl: String,
    val artistIds: List<String>,
    val about: String,
    val albumLength: String,
    val songs: List<SongModel>,
    val bgColor: String = ""
)
