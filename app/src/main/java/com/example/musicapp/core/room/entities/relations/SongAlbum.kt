package com.example.musicapp.core.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.musicapp.core.room.entities.album.Album
import com.example.musicapp.core.room.entities.song.Song

data class SongAlbum(
    @Embedded val song: Song,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val album: Album
)
