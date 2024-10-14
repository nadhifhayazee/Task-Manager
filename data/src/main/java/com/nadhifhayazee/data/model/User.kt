package com.nadhifhayazee.data.model

import com.nadhifhayazee.domain.dto.ResponseUser

data class User(
    val email: String,
    val username: String,
    val password: String,
    val photoUrl: String?
) {
    fun toResponseUser(id:String): ResponseUser {
        return ResponseUser(
            id,
            this.email,
            this.username,
            this.photoUrl ?: ""
        )
    }
}
