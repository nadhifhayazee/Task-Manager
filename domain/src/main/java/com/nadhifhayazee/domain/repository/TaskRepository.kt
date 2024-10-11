package com.nadhifhayazee.domain.repository

import com.nadhifhayazee.domain.dto.RequestTask
import com.nadhifhayazee.domain.dto.ResponseTask

interface TaskRepository {
    suspend fun getUserTasks(userId: String): List<ResponseTask>
    suspend fun addUserTask(userId: String, request: RequestTask): Boolean
    suspend fun updateUserTask(userId: String, taskId: String, request: RequestTask): Boolean
}