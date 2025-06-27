package com.example.musicapp.core.room.entities.artist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Artist")
data class Artist(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String
)
