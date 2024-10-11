package com.nadhifhayazee.domain.usecase

import com.nadhifhayazee.domain.cache.LocalCache
import com.nadhifhayazee.domain.dto.RequestTask
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddUserTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val localCache: LocalCache
) {

    operator fun invoke(request: RequestTask): Flow<ResultState<Boolean>> {
        return flow {
            try {
                emit(ResultState.Loading())

                localCache.getUserId()?.let { userId ->
                    val result = taskRepository.addUserTask(userId, request)
                    emit(ResultState.Success(result))
                }
            } catch (e: Exception) {
                emit(ResultState.Error(e))
            }
        }
    }
}