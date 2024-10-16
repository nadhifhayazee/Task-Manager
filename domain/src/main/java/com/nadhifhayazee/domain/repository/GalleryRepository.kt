package com.nadhifhayazee.domain.repository

import com.nadhifhayazee.domain.dto.ResponseGallery

interface GalleryRepository {
    suspend fun getGalleryFiles(): List<ResponseGallery>
}