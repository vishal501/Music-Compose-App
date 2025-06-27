package com.example.musicapp.screens.home.composables

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.musicapp.R
import com.example.musicapp.model.AlbumOverviewModel
import com.example.musicapp.screens.songDetail.RemoteImage

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TrendingAlbums(
    albums: List<AlbumOverviewModel>,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    selectedAlbum: (AlbumOverviewModel) -> Unit
){
    val interactionSource = remember { MutableInteractionSource() }
    Text(
        text = stringResource(R.string.treading_albums),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(start = 20.dp)
    )
    LazyRow(contentPadding = PaddingValues(20.dp)) {
        items(count = albums.size, key = {albums[it].id}){
            AlbumItem(
                albumModel = albums[it],
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        selectedAlbum(albums[it])
                    },
                sharedTransitionScope,
                animatedContentScope
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AlbumItem(
    albumModel: AlbumOverviewModel,
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
){
    with(sharedTransitionScope){
        Column(modifier = modifier) {
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)) {
                Box(modifier = Modifier
                    .size(150.dp)
                ) {
                    RemoteImage(
                        url = albumModel.imageUrl,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(MaterialTheme.shapes.medium.topEnd))
                            .sharedBounds(
                                sharedTransitionScope.rememberSharedContentState(key = "albumImage-${albumModel.id}"),
                                animatedVisibilityScope = animatedContentScope,
                                enter = fadeIn(),
                                exit = fadeOut(),
                                resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                                boundsTransform = BoundsTransform { initialBounds, targetBounds ->
                                    tween(durationMillis = 1000)
                                },
                                placeHolderSize = SharedTransitionScope.PlaceHolderSize.animatedSize
                            )
                            .matchParentSize()
                            .shadow(
                                elevation = 10.dp,
                                spotColor = Color.Black,
                                shape = RoundedCornerShape(MaterialTheme.shapes.medium.topEnd)
                            )
                            .graphicsLayer {
                                rotationX = 5f
                                rotationY = 5f
                            }


//                            .border(width = 1.dp, shape = RoundedCornerShape(MaterialTheme.shapes.medium.topEnd), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))




                            ,
                    )
                }

                Canvas(
                    modifier = Modifier
                        .padding(start = 120.dp)
                        .size(150.dp)
                        .sharedBounds(
                            sharedContentState = sharedTransitionScope.rememberSharedContentState(
                                key = "albumDisk-${albumModel.id}"
                            ),
                            animatedVisibilityScope = animatedContentScope,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                            boundsTransform = BoundsTransform { initialBounds, targetBounds ->
                                tween(durationMillis = 1000)
                            }
                        )
                        .shadow(
                            elevation = 10.dp,
                            spotColor = Color.Black,
                            shape = CircleShape
                        )
                        .align(Alignment.TopEnd)
                ) {
                    val centerX = size.width / 2f
                    val centerY = size.height / 2f
                    val maxRadius = size.width / 2
                    val minRadius = maxRadius / 3
                    var radius = minRadius
                    while (radius < maxRadius) {
                        drawCircle(
                            color = Color.Black,
                            style = Stroke(width = 2f),
                            radius = radius,
                            center = Offset(centerX, centerY),
                            blendMode = BlendMode.Luminosity
                        )
                        radius += 2f
                    }

                    drawCircle(
                        color = Color(android.graphics.Color.parseColor(albumModel.bgColor)),
                        style = Fill,
                        radius = minRadius,
                        center = Offset(centerX, centerY)
                    )
                    drawCircle(
                        color = Color(android.graphics.Color.parseColor(albumModel.bgColor)),
                        style = Stroke(width = 2f),
                        radius = minRadius,
                        center = Offset(centerX, centerY)
                    )

                    drawCircle(
                        color = Color.White,
                        style = Fill,
                        radius = 10f,
                        center = Offset(centerX, centerY)
                    )

                }
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                text = albumModel.name+" •"+albumModel.artistName,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Start)
                    .sharedBounds(
                        sharedTransitionScope.rememberSharedContentState(key = "albumName-${albumModel.id}"),
                        animatedVisibilityScope = animatedContentScope,
                        enter = fadeIn(),
                        exit = fadeOut(),
                        boundsTransform = BoundsTransform { initialBounds, targetBounds ->
                            tween(durationMillis = 1000)
                        }
                    )
            )

        }
    }

}

//@Preview(showBackground = true)
//@Composable
//fun PreviewAlbumItem(){
//    AlbumItem(
//        albumModel = AlbumOverviewModel(
//            id = "",
//            name = "",
//            imageUrl = "",
//            artistName = "",
//            title = "",
//            releaseDate = ""
//        ),
//        modifier = Modifier,
//    )
//}
