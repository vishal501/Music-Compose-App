package com.example.musicapp.core

import android.content.Context
import androidx.room.Room
import com.example.musicapp.core.room.entities.AppDataBase

private const val DATABASE_NAME = "song_database"
object DataStore {

    lateinit var dataBase: AppDataBase
        private set

    fun provide(context: Context){
        dataBase = Room.databaseBuilder(
            context,
            AppDataBase::class.java, DATABASE_NAME
        ).build()
    }

}