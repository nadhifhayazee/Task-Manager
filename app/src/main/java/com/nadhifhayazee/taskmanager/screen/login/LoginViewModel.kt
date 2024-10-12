package com.nadhifhayazee.taskmanager.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhifhayazee.domain.dto.ResponseUser
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {


    private val _loginState = MutableSharedFlow<ResultState<ResponseUser>>()
    val loginState: SharedFlow<ResultState<ResponseUser>> get() = _loginState.asSharedFlow()

    fun login(email:String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password)
                .collectLatest {
                    _loginState.emit(it)
            }
        }
    }
}