package com.nadhifhayazee.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nadhifhayazee.domain.dto.RequestTask
import com.nadhifhayazee.domain.dto.ResponseTask
import com.nadhifhayazee.domain.repository.TaskRepository
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val firebaseDb: FirebaseFirestore
) : TaskRepository {
    override suspend fun getUserTasks(userId: String): List<ResponseTask> {
        try {
            val snapshot = firebaseDb.collection("users").document(userId)
                .collection("tasks").get().await()
            return if (snapshot.documents.isNotEmpty()) {
                snapshot.documents.map { ResponseTask.convertFromMap(it) }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            throw Exception("Gagal mengambil data!")
        }
    }

    override suspend fun addUserTask(userId: String, request: RequestTask): Boolean {
        try {
            val taskId = UUID.randomUUID().toString()
            firebaseDb.collection("users").document(userId)
                .collection("tasks").document(taskId).set(request).await()
            return true
        } catch (e: Exception) {
            throw Exception("Tambah task gagal.")
        }
    }

    override suspend fun updateUserTask(userId: String, taskId: String, request: RequestTask): Boolean {
        try {
            firebaseDb.collection("users").document(userId)
                .collection("tasks").document(taskId).set(request).await()
            return true
        } catch (e: Exception) {
            throw Exception("Tambah task gagal.")
        }
    }
}