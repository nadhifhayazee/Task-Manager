package com.nadhifhayazee.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nadhifhayazee.data.model.User
import com.nadhifhayazee.domain.dto.RequestRegister
import com.nadhifhayazee.domain.dto.ResponseUser
import com.nadhifhayazee.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val firebaseDb: FirebaseFirestore
) : UserRepository {


    override suspend fun getAllUser(): List<ResponseUser> {
        return emptyList();
    }

    override suspend fun getUserByEmailPassword(email: String, password: String): ResponseUser? {
        try {
            val snapshot = firebaseDb.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get().await()
            if (snapshot.documents.isNotEmpty()) {
                return ResponseUser.convertFromMap(snapshot.documents.first())
            }
            return null
        } catch (e: Exception) {
            return null
        }


    }

    override suspend fun getUserByEmail(email: String): ResponseUser? {
        try {
            val snapshot = firebaseDb.collection("users")
                .whereEqualTo("email", email)
                .get().await()
            if (snapshot.documents.isNotEmpty()) {
                return ResponseUser.convertFromMap(snapshot.documents.first())
            }
            return null
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun getUserById(id: String): ResponseUser? {
        try {
            val snapshot = firebaseDb.collection("users")
                .document(id)
                .get().await()
            if (snapshot.exists()) {
                val user =  snapshot.data
                return ResponseUser(
                    id,
                    user?.get("email").toString(),
                    user?.get("username").toString(),
                    ""
                )
            }
            return null
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun addUser(request: RequestRegister): ResponseUser {
        try {
            val user = User(
                request.email,
                request.username,
                request.password,
                null
            )
            val id = UUID.randomUUID().toString()
            firebaseDb.collection("users").document(id).set(user).await()
            return ResponseUser(
                id,
                user.email,
                user.username,
                ""
            )


        } catch (e: Exception) {
            throw Exception("Register gagal")
        }
    }
}