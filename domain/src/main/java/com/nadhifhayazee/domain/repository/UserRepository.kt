package com.nadhifhayazee.domain.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.nadhifhayazee.domain.dto.RequestRegister
import com.nadhifhayazee.domain.dto.ResponseUser

interface UserRepository {
    suspend fun getAllUser(): List<ResponseUser>
    suspend fun getUserByEmailPassword(email: String, password: String): ResponseUser?
    suspend fun getUserByEmail(email: String): ResponseUser?
    suspend fun addUser(request: RequestRegister): ResponseUser
}