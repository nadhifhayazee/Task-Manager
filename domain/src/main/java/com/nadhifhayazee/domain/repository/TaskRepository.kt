package com.nadhifhayazee.domain.repository

import com.nadhifhayazee.domain.dto.RequestTask
import com.nadhifhayazee.domain.dto.ResponseTask

interface TaskRepository {
    suspend fun getUserTasks(userId: String, status: String?): List<ResponseTask>
    suspend fun addUserTask(userId: String, request: RequestTask): String
    suspend fun updateUserTask(userId: String, taskId: String, request: RequestTask): Boolean
    suspend fun updateTaskField(userId: String, taskId: String, field: String, value: Any): Boolean
}