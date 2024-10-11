package com.nadhifhayazee.domain.usecase

import com.nadhifhayazee.domain.cache.LocalCache
import com.nadhifhayazee.domain.dto.ResponseTask
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val localCache: LocalCache
) {
   operator fun invoke(): Flow<ResultState<List<ResponseTask>>> {
       return flow {
           try {
               if (localCache.getUserId() == null) {
                   emit(ResultState.Error(Exception("No user ID found")))
               } else {
                   val tasks = taskRepository.getUserTasks(localCache.getUserId()!!)
                   emit(ResultState.Success(tasks))
               }
           } catch (e:Exception) {
               emit(ResultState.Error(e))
           }
       }
   }
}