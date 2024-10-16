package com.nadhifhayazee.domain.usecase

import com.nadhifhayazee.domain.dto.ResponseGallery
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.repository.GalleryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGalleryFilesUseCase @Inject constructor(
    private val galleryRepository: GalleryRepository
) {
    operator fun invoke(): Flow<ResultState<List<ResponseGallery>>> {
        return flow {
            emit(ResultState.Loading())
            try {
                val response = galleryRepository.getGalleryFiles()
                emit(ResultState.Success(response))
            } catch (e: Exception) {
                emit(ResultState.Error(e))
            }
        }
    }
}