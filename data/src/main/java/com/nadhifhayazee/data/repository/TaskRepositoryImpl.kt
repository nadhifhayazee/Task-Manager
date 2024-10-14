package com.nadhifhayazee.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.nadhifhayazee.data.model.Task
import com.nadhifhayazee.domain.dto.RequestTask
import com.nadhifhayazee.domain.dto.ResponseTask
import com.nadhifhayazee.domain.repository.TaskRepository
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val firebaseDb: FirebaseFirestore
) : TaskRepository {
    override suspend fun getUserTasks(userId: String, status: String?): List<ResponseTask> {
        try {
            var snapshot: QuerySnapshot
            var reference  = firebaseDb.collection("users").document(userId)
                .collection("tasks")
            snapshot = if (status != null) {
                reference.whereEqualTo("status", status).get().await()
            } else {
                reference.get().await()
            }
            return if (snapshot.documents.isNotEmpty()) {
                snapshot.documents.map { ResponseTask.convertFromMap(it) }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            throw Exception("Gagal mengambil data!")
        }
    }

    override suspend fun addUserTask(userId: String, request: RequestTask): String {
        try {
            val taskId = UUID.randomUUID().toString()
            firebaseDb.collection("users").document(userId)
                .collection("tasks").document(taskId).set(Task.mapFromRequestTask(request)).await()
            return taskId
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

    override suspend fun updateTaskField(
        userId: String,
        taskId: String,
        field: String,
        value: Any
    ): Boolean {
        try {
            firebaseDb.collection("users").document(userId)
                .collection("tasks").document(taskId).update(field, value).await()
            return true
        } catch (e: Exception) {
            throw Exception("Tambah task gagal.")
        }
    }
}