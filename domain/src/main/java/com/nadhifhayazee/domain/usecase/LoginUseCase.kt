package com.nadhifhayazee.domain.usecase

import com.nadhifhayazee.domain.cache.LocalCache
import com.nadhifhayazee.domain.dto.ResponseUser
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val passwordHashingUseCase: PasswordHashingUseCase,
    private val localCache: LocalCache
) {
    operator fun invoke(email: String, password: String): Flow<ResultState<ResponseUser>> {
        return flow {
            emit(ResultState.Loading())
            try {
                val hashedPassword = passwordHashingUseCase(password)
                val user = userRepository.getUserByEmailPassword(email, hashedPassword)
                if (user != null) {
                    localCache.setUserId(user.id)
                    emit(ResultState.Success(user))
                } else {
                    emit(ResultState.Error(Exception("Email atau password salah!")))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}