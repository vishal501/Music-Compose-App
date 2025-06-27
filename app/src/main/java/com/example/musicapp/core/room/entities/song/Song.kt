package com.example.musicapp.core.room.entities.song

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Song")
data class Song(
    @PrimaryKey val id: String,
    val name: String,
    val albumId: String,
    val artistId: String,
    val songUrl: String,
    val imageUrl: String,
    val releasedData: Long,
    val ranking: Long,
    val bgColor: String
)
