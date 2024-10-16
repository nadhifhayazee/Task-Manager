package com.nadhifhayazee.data.repository

import com.google.firebase.storage.FirebaseStorage
import com.nadhifhayazee.domain.dto.ResponseGallery
import com.nadhifhayazee.domain.repository.GalleryRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage
) : GalleryRepository{

    private val galleryRef = storage.reference.child("gallery")

    override suspend fun getGalleryFiles(): List<ResponseGallery> {
        val listResult = galleryRef.listAll().await()

        // Map over the items asynchronously to fetch metadata
        val response = listResult.items.map { result ->
            val metadata = result.metadata.await() // Await metadata retrieval
            ResponseGallery(
                fileType = metadata.contentType,
                fileRef = result
            )
        }

        return response
    }
}