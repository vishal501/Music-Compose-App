package com.example.musicapp.playerHandler

import android.content.Context
import android.util.Log
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors

class MediaControlManager(
    context: Context,
    sessionToken: SessionToken
) {

    private lateinit var mediaController: MediaController
    internal var listener: MediaControllerConnectionCallback? = null

    interface MediaControllerConnectionCallback{
        fun onConnected(mediaController: MediaController)
    }

    init {
        val controlFuture = MediaController.Builder(context, sessionToken).buildAsync()
        controlFuture.addListener({
            mediaController =  controlFuture.get()
            mediaController.prepare()
            Log.d("vcgvaghcha", "conneted: ${mediaController.isConnected}")
            listener?.onConnected(mediaController)
        },
            MoreExecutors.directExecutor()
        )
    }

    fun getMediaController(): MediaController{
        return mediaController
    }
}