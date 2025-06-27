package com.example.musicapp

import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.musicapp.playerHandler.MediaControlManager
import com.example.musicapp.playerHandler.PlayerControllerFactory
import com.example.musicapp.service.PlayerService
import com.example.musicapp.ui.theme.MusicAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), MediaControlManager.MediaControllerConnectionCallback {

    private var isMediaControllerReady: MutableState<Boolean> = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MusicAppTheme {
                if(isMediaControllerReady.value){
                    MusicApp()
                }else{
                    Surface(color = MaterialTheme.colorScheme.onBackground) {

                    }
                }
            }
        }
        val sessionToken = SessionToken(this, ComponentName(this, PlayerService::class.java))
        val mediaControlManager = MediaControlManager(this, sessionToken)
        mediaControlManager.listener = this
    }

    override fun onConnected(mediaController: MediaController) {
        PlayerControllerFactory().initialiseMediaController(mediaController, packageName)
        isMediaControllerReady.value = true
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MusicAppTheme {
        MusicApp()
    }
}