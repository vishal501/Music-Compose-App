package com.example.musicapp.model

import androidx.compose.ui.graphics.Color

data class SongModel(
    val id: String,
    val name: String,
    val artists: List<ArtistModel>,
    val imageUrl: String,
    val uri: String,
    val bgColor: Color
)


