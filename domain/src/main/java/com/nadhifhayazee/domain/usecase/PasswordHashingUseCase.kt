package com.nadhifhayazee.domain.usecase

import java.security.MessageDigest
import javax.inject.Inject

class PasswordHashingUseCase @Inject constructor() {
    operator fun invoke(originPassword: String): String {
        val bytes = MessageDigest.getInstance("MD5").digest(originPassword.toByteArray())
        return bytes.joinToString("") {
            "%02x".format(it)
        }
    }
}