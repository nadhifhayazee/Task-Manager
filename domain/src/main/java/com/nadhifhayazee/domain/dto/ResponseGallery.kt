package com.nadhifhayazee.domain.dto

import com.google.firebase.storage.StorageReference

data class ResponseGallery(
    val fileType: String?,
    val fileRef: StorageReference?,
)
