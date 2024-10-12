package com.nadhifhayazee.taskmanager.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhifhayazee.domain.dto.ResponseUser
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _registerState = MutableSharedFlow<ResultState<ResponseUser>>()
    val registerState: SharedFlow<ResultState<ResponseUser>> = _registerState.asSharedFlow()

    fun register(email:String, username:String, password: String) {
        viewModelScope.launch {
            registerUseCase(email, username, password).collectLatest {
                _registerState.emit(it)
            }
        }
    }
}