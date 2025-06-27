package com.example.musicapp

import android.app.Application
import android.content.ComponentName
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.musicapp.core.DataStore
import com.example.musicapp.core.room.entities.AppDataBase
import com.example.musicapp.core.sampleAlbums
import com.example.musicapp.core.sampleArtists
import com.example.musicapp.core.sampleSongs
import com.example.musicapp.service.PlayerService
import com.google.common.util.concurrent.MoreExecutors
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking

@HiltAndroidApp
class SongApplication: Application() {

    private lateinit var dataBase: AppDataBase
    override fun onCreate() {
        super.onCreate()
        DataStore.provide(applicationContext)
        dataBase = DataStore.dataBase



        runBlocking {
            //val k = dataBase.getAlbumDao().getAlbumsSongs()
            //if(k.isEmpty()){
            dataBase.getAlbumDao().insertAll(sampleAlbums)
            dataBase.getArtistDao().insertAll(sampleArtists)
            dataBase.getAlbumDao().insertAll(sampleAlbums)
            dataBase.getSongDao().insertAll(sampleSongs)
            //}
        }
    }


}