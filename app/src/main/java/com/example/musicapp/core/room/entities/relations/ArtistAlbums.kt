package com.example.musicapp.core.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.musicapp.core.room.entities.album.Album
import com.example.musicapp.core.room.entities.artist.Artist

data class ArtistAlbums(
    @Embedded val artist: Artist,
    @Relation(
        parentColumn = "id",
        entityColumn = "artistId"
    )
    val albums: List<Album>
)
