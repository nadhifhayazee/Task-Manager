package com.nadhifhayazee.domain.usecase

import com.nadhifhayazee.domain.cache.LocalCache
import com.nadhifhayazee.domain.dto.RequestRegister
import com.nadhifhayazee.domain.dto.ResponseUser
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val passwordHashingUseCase: PasswordHashingUseCase,
    private val localCache: LocalCache
) {
    operator fun invoke(
        email: String,
        username: String,
        password: String
    ): Flow<ResultState<ResponseUser>> {
        return flow {
            try {
                emit(ResultState.Loading())
                val user = userRepository.getUserByEmail(email)
                if (user != null) {
                    emit(ResultState.Error(Exception("Email sudah digunakan.")))
                } else {
                    val request = RequestRegister(
                        email,
                        username,
                        passwordHashingUseCase(password)
                    )
                    val newUser = userRepository.addUser(request)
                    localCache.setUserId(newUser.id)
                    emit(ResultState.Success(newUser))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}