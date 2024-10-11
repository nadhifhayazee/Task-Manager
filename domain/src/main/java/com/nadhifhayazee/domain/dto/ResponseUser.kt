package com.nadhifhayazee.domain.dto

import com.google.firebase.firestore.DocumentSnapshot

data class ResponseUser (
    val id: String,
    val email: String,
    val username: String,
    val photoUrl: String
) {
    companion object {
        fun convertFromMap(document: DocumentSnapshot): ResponseUser {
            return ResponseUser(
                document.id,
                document.data?.get("email").toString(),
                document.data?.get("username").toString(),
                document.data?.get("photoUrl").toString(),
            )
        }
    }
}