package com.example.musicapp.domain

import com.example.musicapp.model.ArtistModel

interface ArtistRepository {

    suspend fun getArtistDetail(ids: List<String>): List<ArtistModel>
}