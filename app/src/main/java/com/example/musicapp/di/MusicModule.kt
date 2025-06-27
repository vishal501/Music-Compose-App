package com.example.musicapp.di

import com.example.musicapp.data.SongRepositoryImpl
import com.example.musicapp.domain.AlbumRepository
import com.example.musicapp.domain.ArtistRepository
import com.example.musicapp.domain.SongRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class MusicModule {

    @Binds
    abstract fun bindSongRepository(
        songRepositoryImpl: SongRepositoryImpl
    ): SongRepository

    @Binds
    abstract fun bindAlbumRepository(
        songRepositoryImpl: SongRepositoryImpl
    ): AlbumRepository

    @Binds
    abstract fun bindArtistRepository(
        songRepositoryImpl: SongRepositoryImpl
    ): ArtistRepository
}