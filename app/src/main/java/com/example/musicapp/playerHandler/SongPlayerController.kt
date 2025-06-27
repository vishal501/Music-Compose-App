package com.example.musicapp.playerHandler

import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import com.example.musicapp.common.SongState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

private class SongPlayerController: PlayerController {

    private lateinit var mediaController: MediaController
    private lateinit var packageName: String
    private val playList: MutableList<MediaDetail> = mutableListOf()
    private val externalPlayerControllerListener = ExternalPlayerListener()


    fun setMediaController(controller: MediaController){
        this.mediaController = controller
        mediaController.addListener(externalPlayerControllerListener)
    }

    fun setPackageName(name: String){
        this.packageName = name
    }

    override fun addMedia(list: List<MediaDetail>) {
        if(playList == list){
            return
        }else if (playList.isNotEmpty() ){
            mediaController.clearMediaItems()
        }
        playList.clear()
        playList.addAll(list)
        mediaController.addMediaItems(list.map { media ->
            MediaItem.fromUri(media.uri)
        })
        mediaController.prepare()
    }

    override fun getCurrentPlayingMediaDetail(): MediaDetail {
        return playList[mediaController.currentMediaItemIndex]
    }

    override fun getSongPlayStateFlow(): Flow<SongState> {
        return externalPlayerControllerListener.songPlayPauseFlow
    }

    override fun moveToNextSong() {
        mediaController.seekToNext()
    }

    override fun moveToPreviousSong() {
        mediaController.seekToPrevious()
    }

    override fun getSeekPosition(): Long {
        return mediaController.currentPosition
    }


    override fun play() {
        if(mediaController.currentPosition > 0){
            mediaController.seekTo(0, 0)
        }
        mediaController.play()

        Log.d("vhxvahdsjha", "play: ${mediaController.mediaMetadata}")
    }

    override fun play(index: Int?, seekPos: Long) {
        val currentIndex = index ?: mediaController.currentMediaItemIndex
        mediaController.seekTo(currentIndex, seekPos)
        mediaController.play()
    }

    override fun pause() {
        mediaController.pause()
    }

    override fun seekFlow(): Flow<Long> {
        return flow<Long> {
            while(true){
                emit(mediaController.currentPosition)
                delay(950)
            }
        }.distinctUntilChanged().map { it.coerceAtLeast(0) }
    }


    override suspend fun getSongDuration(): Long {
        while(mediaController.duration < 0){
            delay(300)
            return getSongDuration()
        }
        return mediaController.duration
    }

    override fun seekTo(ms: Long) {
        mediaController.seekTo(ms)
    }

    override fun isSongPlaying(): Boolean {
       return mediaController.isPlaying

    }

    override fun isConnected(): Boolean {
        return mediaController.isConnected
    }

    override fun getTrackChange(): Flow<Pair<Int, MediaDetail>> {
        return externalPlayerControllerListener.trackChange.map { track ->
            val index = mediaController.currentMediaItemIndex
            Pair(index, getCurrentPlayingMediaDetail())
        }
    }

    override fun playerStateFlow(): Flow<Int> {
        return externalPlayerControllerListener.playerState
    }
}

class PlayerControllerFactory{

    private companion object{
        private var playerController: PlayerController? = null
    }

    fun initialiseMediaController(mediaController: MediaController, packageName: String): PlayerController {
        if(playerController ==  null){
            playerController = SongPlayerController().apply {
                setPackageName(packageName)
                setMediaController(mediaController)
            }
        }
        return playerController!!
    }

    fun getPlayerController(): PlayerController {
        return playerController!!
    }

}