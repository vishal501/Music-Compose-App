package com.example.musicapp.core.room.entities.album

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Album")
data class Album(
    @PrimaryKey val albumId: String,
    val title: String,
    val imageUrl: String,
    val releasedData: Long,
    val artistId: String,
    val bgColor: String,
    val songIds: ArrayList<String>
)
