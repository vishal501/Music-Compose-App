package com.example.musicapp.domain

import androidx.compose.ui.graphics.Color
import com.example.musicapp.core.room.entities.album.Album
import com.example.musicapp.core.room.entities.artist.Artist
import com.example.musicapp.core.room.entities.relations.SongAlbum
import com.example.musicapp.core.room.entities.song.Song
import com.example.musicapp.model.AlbumOverviewModel
import com.example.musicapp.model.ArtistModel
import com.example.musicapp.model.SongModel

fun  Song.toSongModel(): SongModel{
    return SongModel(
        id = this.id,
        name = this.name,
        artists = listOf(ArtistModel("","",this.artistId)),
        imageUrl = this.imageUrl,
        uri = this.songUrl,
        bgColor = Color(android.graphics.Color.parseColor(this.bgColor))
    )
}

fun Album.toAlbumOverviewModel(): AlbumOverviewModel{
    return AlbumOverviewModel(
        id = this.albumId,
        title = this.title,
        name = this.title,
        imageUrl = this.imageUrl,
        artistName = "",
        releaseDate = this.releasedData.toString(),
        bgColor = this.bgColor
    )
}
//
//fun SongAlbum.toAlbumDetail(): AlbumDetail{
//    return AlbumDetail(
//        id = this.album.albumId,
//        title = this.album.title,
//        imageUrl = this.album.imageUrl,
//        artistIds = listOf(this.album.artistId),
//        about = "Sample data",
//        albumLength = "4hr 50min",
//        bgColor = this.album.bgColor,
//        songs = this.songs.map { it.toSongModel() }
//    )
//}

fun Album.toAlbumModel(songModel: List<Song>): AlbumDetail{
    return AlbumDetail(
        id = this.albumId,
        title = this.title,
        imageUrl = this.imageUrl,
        artistIds = listOf(this.artistId),
        about = "Sample data",
        albumLength = "4hr 50min",
        bgColor = this.bgColor,
        songs = songModel.map { it.toSongModel() }
    )
}

fun Artist.toArtistModel(): ArtistModel{
    return ArtistModel(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl
    )
}