package com.example.musicapp.domain

import com.example.musicapp.model.SongModel

interface SongRepository {
    suspend fun getSongDetail(id: String): SongModel
    suspend fun getAllSongs(): List<SongModel>
}