package com.nadhifhayazee.data.repository

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.nadhifhayazee.domain.repository.StorageRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
) : StorageRepository{

    val ONE_MEGABYTE: Long = 1024 * 1024
    private val taskRef = storage.reference.child("task")

    override suspend fun getTaskFileUrl(taskId: String, filename: String): Uri {
        val ref = taskRef.child(taskId).child(filename)
        return ref.downloadUrl.await()
    }

    override suspend fun uploadTaskFile(taskId: String, file: Uri): String {
        val ref = taskRef.child(taskId).child(file.lastPathSegment ?: "file.jpg")
        val uploadTask = ref.putFile(file).await()
        if (!uploadTask.task.isSuccessful) {
            uploadTask.task.exception?.let {
                throw it
            }
        }
        return ref.downloadUrl.await().toString()
    }
}