package com.nadhifhayazee.domain.usecase

import com.nadhifhayazee.domain.cache.LocalCache
import com.nadhifhayazee.domain.dto.ResponseUser
import com.nadhifhayazee.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val localCache: LocalCache
) {

    operator fun invoke(): Flow<ResponseUser?> {
        return flow {
            try {
                val userId = localCache.getUserId() ?: throw Exception("user not found");
                val user = userRepository.getUserById(userId)
                emit(user)
            } catch (e:Exception) {
                emit(null)
            }
        }
    }
}