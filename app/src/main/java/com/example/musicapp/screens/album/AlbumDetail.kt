package com.example.musicapp.screens.album

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.example.musicapp.domain.AlbumDetail
import com.example.musicapp.model.ArtistModel
import com.example.musicapp.screens.songDetail.RemoteImage
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private val AppBarHeight = 50.dp
val ImageAspectRatio = 1f



@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AlbumDetailScreen(
    modifier: Modifier,
    animatedContentScope: AnimatedContentScope,
    uiState: AlbumUIContract.State,
    actionHandler: (AlbumUIContract.Event) -> Unit
) {
    val albumDetail = uiState.albumDetail
    val songListState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        actionHandler(AlbumUIContract.Event.FetchAlbumDetail)
    }
    AlbumWrapper(
        modifier = modifier,
        albumDetail = uiState.albumDetail,
        artists = uiState.artists,
        animatedContentScope = animatedContentScope,
        playAlbumAction = {actionHandler(AlbumUIContract.Event.PlayAlbum)}
    ) { padding ->
        Spacer(modifier = Modifier.padding(10.dp))
        AlbumSongs(listState = songListState, albumDetail.songs,modifier = Modifier.weight(1f), actionHandler)
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.AlbumWrapper(
    modifier: Modifier,
    albumDetail: AlbumDetail,
    artists: List<ArtistModel>,
    animatedContentScope: AnimatedContentScope,
    playAlbumAction: () -> Unit,
    content: @Composable ColumnScope.(PaddingValues) -> Unit
){
    ParallaxAlbumLayout(
        modifier = modifier,
        albumDetail = albumDetail,
        floatingDisk = { appBarHeightInPx, collapseFraction, offset ->
            FloatingAlbumDisk(
                id = albumDetail.id,
                scrollDelta = {
                        offset() - lerp(0f, appBarHeightInPx*3/2, collapseFraction()) },
                centerColor = Color(android.graphics.Color.parseColor(albumDetail.bgColor)),
                animatedContentScope =  animatedContentScope,
            ){
                playAlbumAction()
            }
        },
        headerContent = { collapseFraction: () ->Float, offset: Float, headerLayoutHeight ->
            CollapsableHeader(
                modifier = Modifier.offset {
                    IntOffset(0, offset.roundToInt())
                },
                albumDetail = albumDetail,
                artists = artists,
                animatedContentScope = animatedContentScope,
                collapseFraction = {
                    collapseFraction()
                },
                playAlbum = playAlbumAction,
                layoutHeight = {
                    headerLayoutHeight(it)
                }
            )
        }) {
        content(PaddingValues(10.dp))
    }
}

@Composable
fun ParallaxAlbumLayout(
    modifier: Modifier,
    albumDetail: AlbumDetail,
    floatingDisk: @Composable (appBarHeight: Float, collapseFraction: () -> Float, offset: () -> Float) -> Unit,
    headerContent: @Composable (collapseFraction: () -> Float, offset: Float, layoutHeight: (Float) -> Unit) -> Unit,
    bodyContent: @Composable (ColumnScope.() -> Unit),
){
    val appBarHeightInPx: Float = with(LocalDensity.current){
        AppBarHeight.toPx() + WindowInsets.systemBars.getBottom(this)
    }
    var imageLayoutHeight by remember {
        mutableFloatStateOf(0f)
    }

    val nestedScrollConnection = remember(imageLayoutHeight) {
        AlbumDetailNestedScrollConnection(
            imageHeight = imageLayoutHeight - appBarHeightInPx
        )
    }
    Box {
        Scaffold(
            modifier = modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
                .nestedScroll(nestedScrollConnection),
            topBar = {
                AppBarSetUp(
                    showAppBar = nestedScrollConnection.showAppBarState,
                    title = albumDetail.title,
                    appBarContainerColor = handleAppBarContainerColor(albumDetail.bgColor, nestedScrollConnection.showAppBarState)
                )
            }
        ){ padding ->
            Column{
                Spacer(modifier = Modifier
                    .background(color = Color.Blue)
                    .height(with(LocalDensity.current) { (imageLayoutHeight - nestedScrollConnection.imageOffset.absoluteValue).toDp() })
                )
                bodyContent()
            }
            headerContent({nestedScrollConnection.collapseFraction}, nestedScrollConnection.imageOffset){
                imageLayoutHeight = it
            }
        }
        floatingDisk(appBarHeightInPx, {nestedScrollConnection.collapseFraction}, {nestedScrollConnection.imageOffset})
//        FloatingAlbumDisk(
//            id = albumDetail.id,
//            collapseAppBarHeight = appBarHeightInPx,
//            collapseFraction = { nestedScrollConnection.collapseFraction },
//            centerColor = Color(android.graphics.Color.parseColor(albumDetail.bgColor)),
//            animatedContentScope =  animatedContentScope,
//        ){
//            playAlbum()
//        }
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CollapsableHeader(
    modifier: Modifier,
    albumDetail: AlbumDetail,
    artists: List<ArtistModel>,
    playAlbum: () -> Unit,
    animatedContentScope: AnimatedContentScope,
    layoutHeight: (Float) -> Unit,
    collapseFraction: () -> Float,

) {
    val appBarHeightInPx: Float = with(LocalDensity.current){
        AppBarHeight.toPx() + WindowInsets.systemBars.getBottom(this)
    }

    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(MaterialTheme.shapes.large.topEnd))
            .sharedBounds(
                rememberSharedContentState(key = "albumImage-${albumDetail.id}"),
                animatedVisibilityScope = animatedContentScope,
                enter = fadeIn(),
                exit = fadeOut(),
                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                boundsTransform = { initialBounds, targetBounds ->
                    tween(durationMillis = 1000)
                },
            )
            .then(modifier)
            .onGloballyPositioned {
                layoutHeight(it.size.height.toFloat())
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier

        ) {
            RemoteImage(
                url = albumDetail.imageUrl,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(MaterialTheme.shapes.large.topEnd))
                    .graphicsLayer {
                        alpha = lerp(1f, 0.3f, collapseFraction())
                        scaleY = lerp(1f, 0.3f, collapseFraction())
                        scaleX = lerp(1f, 0.3f, collapseFraction())
                    }
                    .aspectRatio(ImageAspectRatio),
                contentScale = ContentScale.Crop
            )
            AlbumName(
                album = albumDetail.title,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .sharedBounds(
                        rememberSharedContentState(key = "albumName-${albumDetail.id}"),
                        animatedVisibilityScope = animatedContentScope,
                        enter = fadeIn(),
                        exit = fadeOut(),
                        boundsTransform = BoundsTransform { initialBounds, targetBounds ->
                            tween(durationMillis = 1000)
                        })
            )
            AlbumArtists(
                artists = artists,
                modifier =  Modifier.padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.padding(5.dp))
            TotalSongAndLengthOfAlbum(albumDetail.songs.size, albumDetail.albumLength)
        }
    }

}


@Composable
fun TotalSongAndLengthOfAlbum(songCount: Int, albumDuration: String) {
    Text(
        text = "$songCount Songs • $albumDuration",
        style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarSetUp(showAppBar: Boolean, title: String, appBarContainerColor: Color) {
    TopAppBar(
        title = {
            if(showAppBar){
                AlbumName(
                    album = title,
                    modifier = Modifier
                        .height(AppBarHeight)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = appBarContainerColor
        ),
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "arrow back",
                modifier = Modifier.size(25.dp)
            )
        }
    )
}

fun handleAppBarContainerColor(bgColor: String, showAppBar: Boolean): Color {
    return if(showAppBar){
        Color(
            android.graphics.Color.parseColor(bgColor)
        )
    }else{
        Color.Transparent
    }
}

@Composable
fun AlbumName(album: String, modifier: Modifier) {
    Text(
        text = album,
        style = MaterialTheme.typography.headlineLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun AlbumArtists(artists: List<ArtistModel>, modifier: Modifier) {
    Text(
        text = artists.joinToString(separator = ",") { it.name },
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium,
        modifier = modifier
    )
}


@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.FloatingAlbumDisk(
    id: String,
    scrollDelta: () -> Float,
    centerColor: Color,
    animatedContentScope: AnimatedContentScope,
    playAlbumAction: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    AlbumDisk(
        bgColor = centerColor,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .size(100.dp)
            .offset {
                IntOffset(0, scrollDelta().roundToInt())
            }
            .sharedBounds(
                rememberSharedContentState(key = "albumDisk-${id}"),
                animatedVisibilityScope = animatedContentScope,
                enter = fadeIn(),
                exit = fadeOut(),
                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                boundsTransform = { initialBounds, targetBounds ->
                    tween(durationMillis = 1000)
                })
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                playAlbumAction()
            }
            .shadow(
                elevation = 10.dp,
                spotColor = Color.Black,
                shape = CircleShape
            )
    )
}



//
//@SuppressLint("UnusedContentLambdaTargetStateParameter")
//@Composable
//@Preview(showBackground = true)
//fun PreviewAlbumDetail(){
//        ParallaxAlbumLayout(modifier = , albumDetail = {
//
//        }, headerContent = ) {
//
//        }
//
//}


