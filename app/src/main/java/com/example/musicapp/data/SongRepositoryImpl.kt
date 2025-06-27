package com.example.musicapp.data

import com.example.musicapp.core.room.entities.AppDataBase
import com.example.musicapp.domain.AlbumDetail
import com.example.musicapp.domain.AlbumRepository
import com.example.musicapp.domain.ArtistRepository
import com.example.musicapp.domain.SongRepository
import com.example.musicapp.domain.toAlbumModel
import com.example.musicapp.domain.toAlbumOverviewModel
import com.example.musicapp.domain.toArtistModel
import com.example.musicapp.domain.toSongModel
import com.example.musicapp.model.AlbumOverviewModel
import com.example.musicapp.model.ArtistModel
import com.example.musicapp.model.SongModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    private val appDataBase: AppDataBase
): SongRepository, AlbumRepository, ArtistRepository {

    override suspend fun getSongDetail(id: String): SongModel {
        return withContext(Dispatchers.IO){
            appDataBase.getSongDao().fetchSongDetail(id).toSongModel()
        }
    }

    override suspend fun getAllSongs(): List<SongModel> {
        return withContext(Dispatchers.IO){
            appDataBase.getSongDao().getAllSongs().map {
                val artists = getArtistDetail(listOf(it.artistId))
                it.toSongModel().copy(artists = artists)
            }
        }
    }

    override suspend fun getAlbums(): List<AlbumOverviewModel> {
        return withContext(Dispatchers.IO){
            appDataBase.getAlbumDao().getAllAlbums().map {
                val artist = getArtistDetail(listOf(it.artistId))
                it.toAlbumOverviewModel().copy(artistName = artist[0].name)
            }
        }
    }

    override suspend fun getAlbumDetail(id: String): AlbumDetail {
        return withContext(Dispatchers.IO){
            val album = appDataBase.getAlbumDao().getAlbumDetail(id)
            val albumSongs = appDataBase.getSongDao().getSongs(album.songIds)
            album.toAlbumModel(albumSongs)
        }

    }

    override suspend fun getArtistDetail(ids: List<String>): List<ArtistModel> {
        return withContext(Dispatchers.IO){
            ids.map {
                appDataBase.getArtistDao().getArtistDetail(it).toArtistModel()
            }
        }
    }
}