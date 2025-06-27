package com.example.musicapp.core.room.entities.album

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.musicapp.core.room.BaseDao

@Dao
interface AlbumDao: BaseDao<Album> {


    @Query("SELECT * FROM Album")
    fun getAllAlbums(): List<Album>


    @Query("SELECT * FROM Album WHERE albumId = :albumId")
    fun getAlbumDetail(albumId: String): Album

}