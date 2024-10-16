package com.nadhifhayazee.taskmanager.screen.gallery

import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import com.nadhifhayazee.domain.dto.ResponseGallery
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.taskmanager.component.VideoThumbnail
import io.github.rosariopfernandes.firecoil.load

@OptIn(UnstableApi::class)
@Composable
fun GalleryScreen(modifier: Modifier = Modifier, viewModel: GalleryViewModel = hiltViewModel()) {

    val galleryState by viewModel.galleryState.collectAsState()
    // Get current context
    val context = LocalContext.current
    val exoPlayer = ExoPlayer.Builder(context).build()

    // Create a MediaSource
    val EXAMPLE_VIDEO_URI = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    val mediaSource = remember(EXAMPLE_VIDEO_URI) {
        MediaItem.Builder().setUri(EXAMPLE_VIDEO_URI).setImageDurationMs(1000).build();
    }

    LaunchedEffect(Unit){
        viewModel.getGallery();
    }

    // Set MediaSource to ExoPlayer
    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text("Gallery")

        when(galleryState) {
            is ResultState.Loading -> {

            }
            is ResultState.Success -> {
                val files = (galleryState as ResultState.Success<List<ResponseGallery>>).data
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = modifier.fillMaxWidth().weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    items(files) { file ->

                        if (file.fileType?.contains("image") == true) {

                            AndroidView(
                                factory = { ctx ->
                                    ImageView(ctx).apply {
                                        layoutParams = ViewGroup.LayoutParams(
                                            80,
                                            80
                                        )
                                        scaleType = ImageView.ScaleType.CENTER_CROP
                                        file.fileRef?.let { load(it) } //
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .width(80.dp)
                                    .height(80.dp) // Set your desired height
                            )
                        } else {
                            file.fileRef?.let { VideoThumbnail(fileRef = it, modifier.size(80.dp)) }
                        }
                    }
                }

            }
            else -> {}
        }

    }
}