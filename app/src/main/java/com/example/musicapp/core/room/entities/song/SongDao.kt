package com.example.musicapp.core.room.entities.song

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.musicapp.core.room.BaseDao
import com.example.musicapp.core.room.entities.relations.SongAlbum

@Dao
interface SongDao: BaseDao<Song> {

    @Query("SELECT * FROM SONG where id==:id")
    suspend fun fetchSongDetail(id: String): Song

    @Query("SELECT * FROM SONG")
    suspend fun getAllSongs(): List<Song>

    @Transaction
    @Query("SELECT * FROM SONG where id==:id")
    suspend fun getSongAlbum(id: String): SongAlbum

    @Query("SELECT * FROM SONG where id IN (:songsId)")
    suspend fun getSongs(songsId: List<String>): List<Song>
}