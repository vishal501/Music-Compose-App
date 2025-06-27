package com.example.musicapp.core.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.musicapp.core.room.entities.artist.Artist
import com.example.musicapp.core.room.entities.song.Song

data class ArtistSongs(
    @Embedded val artist: Artist,
    @Relation(
        parentColumn = "id",
        entityColumn = "artistId"
    )
    val songs: List<Song>
)
