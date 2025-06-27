package com.example.musicapp.domain

import com.example.musicapp.model.AlbumOverviewModel

interface AlbumRepository {

    suspend fun getAlbums(): List<AlbumOverviewModel>

    suspend fun getAlbumDetail(id: String): AlbumDetail
}