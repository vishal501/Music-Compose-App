package com.example.musicapp.core.room.entities.artist

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.musicapp.core.room.BaseDao
import com.example.musicapp.core.room.entities.relations.ArtistAlbums
import com.example.musicapp.core.room.entities.relations.ArtistSongs

@Dao
interface ArtistDao: BaseDao<Artist>{

    @Transaction
    @Query("SELECT * FROM Artist")
    fun getArtistSongs(): List<ArtistSongs>

    @Transaction
    @Query("SELECT * FROM Artist")
    fun getArtistAlbums(): List<ArtistAlbums>

    @Query("SELECT * FROM Artist WHERE id = :artistId")
    fun getArtistDetail(artistId: String): Artist
}
