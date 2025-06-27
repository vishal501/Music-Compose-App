package com.example.musicapp

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.route.MusicAppRoute
import com.example.musicapp.screens.songDetail.SongDetailScreen
import com.example.musicapp.screens.songDetail.SongDetailVM
import com.example.musicapp.screens.viewer.FloatingPlayer
import com.example.musicapp.screens.viewer.FloatingPlayerVM


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RestrictedApi")
@Composable
fun MusicApp(){
    val navController = rememberNavController()
    val floatingPlayerVM: FloatingPlayerVM = hiltViewModel()
    var showFloatingPlayer by remember {
        mutableStateOf(false)
    }
    var selectedSongId by remember {
        mutableStateOf("")
    }
    var showSongDetailSheet by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val rememberSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            AnimatedVisibility(visible = showFloatingPlayer && selectedSongId.isNotBlank()) {
                FloatingPlayer(modifier = Modifier.padding(5.dp),
                    uiState = floatingPlayerVM.state.collectAsState().value,
                    actionHandler = floatingPlayerVM::handleEvent,
                    selectedSongId
                ){ songId ->
                    showSongDetailSheet = true

                    //navController.navigate(MusicAppScreen.Song_Detail.name)
                }
            }
        }
    ) { padding ->
        MusicAppRoute(
            modifier = Modifier.padding(padding),
            navController = navController){
            showFloatingPlayer = true
            selectedSongId = it
        }
    }

    if(showSongDetailSheet){
        ModalBottomSheet(
            sheetState = rememberSheetState,
            onDismissRequest = { showSongDetailSheet = false },
            dragHandle = null
        ) {
            val vm: SongDetailVM = hiltViewModel()
            SongDetailScreen(modifier = Modifier,
                actionHandler = vm::handleEvent,
                uiState = vm.state.collectAsState().value,
            )
        }
    }

}