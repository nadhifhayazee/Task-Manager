package com.nadhifhayazee.domain.repository

import android.net.Uri

interface StorageRepository {
    suspend fun getTaskFileUrl(taskId:String, filename:String): Uri
    suspend fun uploadTaskFile(taskId: String, file: Uri): String
}