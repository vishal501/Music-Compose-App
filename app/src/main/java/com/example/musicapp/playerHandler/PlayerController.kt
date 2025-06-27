package com.example.musicapp.playerHandler

import androidx.compose.ui.graphics.Color
import com.example.musicapp.common.SongState
import kotlinx.coroutines.flow.Flow

interface PlayerController {

    fun addMedia(list: List<MediaDetail>)

    fun getCurrentPlayingMediaDetail(): MediaDetail

    fun getSongPlayStateFlow(): Flow<SongState>

    fun moveToNextSong()

    fun moveToPreviousSong()

    fun play()

    fun play(index: Int? = null, seekPos: Long)

    fun pause()

    fun seekFlow(): Flow<Long>

    fun getSeekPosition():Long

    suspend fun getSongDuration(): Long

    fun seekTo(ms: Long)

    fun isSongPlaying(): Boolean

    fun isConnected(): Boolean

    fun getTrackChange(): Flow<Pair<Int, MediaDetail>>

    fun playerStateFlow(): Flow<Int>

}

data class MediaDetail(
    val songId: String,
    val uri: String,
    val imageUrl: String,
    val name: String,
    val artistName: String,
    val bgColor: Color
)