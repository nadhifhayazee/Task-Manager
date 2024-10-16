package com.nadhifhayazee.taskmanager.screen.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhifhayazee.domain.dto.ResponseGallery
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.usecase.GetGalleryFilesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getGalleryFilesUseCase: GetGalleryFilesUseCase
)  : ViewModel(){

    private val _galleryState = MutableStateFlow<ResultState<List<ResponseGallery>>>(ResultState.Initial())
    val galleryState get() = _galleryState.asStateFlow()

    fun getGallery() {
        viewModelScope.launch {
            getGalleryFilesUseCase().collectLatest {
                _galleryState.value = it
            }
        }
    }
}