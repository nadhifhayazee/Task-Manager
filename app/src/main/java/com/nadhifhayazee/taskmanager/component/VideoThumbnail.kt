package com.nadhifhayazee.taskmanager.component

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun VideoThumbnail(
    fileRef: StorageReference,
    modifier: Modifier = Modifier
) {
    var thumbnail by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    LaunchedEffect(fileRef) {
        try {
            val localFile = downloadVideoToCache(context, fileRef)

            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(localFile.absolutePath) // Use local path
            val frame = retriever.getFrameAtTime(1_000_000) // 1 second frame
            retriever.release()

            thumbnail = frame
        } catch (e: Exception) {
            Log.e("VideoThumbnail", "Error loading thumbnail", e)
        }
    }

    Box(modifier = modifier.fillMaxSize().clickable {

    }) {
        thumbnail?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Video Thumbnail",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(context: Context, storageRef: StorageReference) {
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    LaunchedEffect(storageRef) {
        try {
            val videoUri = storageRef.downloadUrl.await()

            val mediaItem = MediaItem.fromUri(videoUri)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        } catch (e: Exception) {
            Log.e("VideoPlayer", "Error loading video", e)
        }
    }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}


suspend fun downloadVideoToCache(context: Context, storageRef: StorageReference): File {
    return withContext(Dispatchers.IO) {
        val localFile = File(context.cacheDir, "temp_video.mp4")

        storageRef.getFile(localFile).await()

        localFile
    }
}