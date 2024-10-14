package com.nadhifhayazee.taskmanager.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _selectedMenuIndex = mutableStateOf(0)
    val selectedMenuIndex: State<Int> = _selectedMenuIndex

    fun onMenuSelected(index: Int) {
        _selectedMenuIndex.value = index
    }
}