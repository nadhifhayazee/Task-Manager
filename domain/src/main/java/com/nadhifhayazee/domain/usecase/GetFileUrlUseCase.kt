package com.nadhifhayazee.domain.usecase

import android.net.Uri
import com.nadhifhayazee.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFileUrlUseCase @Inject constructor(
    private val storageRepository: StorageRepository
) {
    operator fun invoke(taskId: String, filename: String): Flow<Uri?> {
        return flow {
            try {
                val uri = storageRepository.getTaskFileUrl(taskId, filename)
                emit(uri)
            } catch (e: Exception) {
                emit(null)
            }
        }
    }
}