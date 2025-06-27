package com.example.musicapp.core.room.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.musicapp.core.room.converter.ArrayListConverter
import com.example.musicapp.core.room.entities.album.Album
import com.example.musicapp.core.room.entities.album.AlbumDao
import com.example.musicapp.core.room.entities.artist.Artist
import com.example.musicapp.core.room.entities.artist.ArtistDao
import com.example.musicapp.core.room.entities.song.Song
import com.example.musicapp.core.room.entities.song.SongDao

@Database(entities = [Song::class, Album::class, Artist::class], version = 1, exportSchema = false)
@TypeConverters(ArrayListConverter::class)
abstract class AppDataBase: RoomDatabase() {

    abstract fun getArtistDao(): ArtistDao

    abstract fun getAlbumDao(): AlbumDao

    abstract fun getSongDao(): SongDao

}