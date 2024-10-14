package com.nadhifhayazee.taskmanager.screen.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhifhayazee.domain.dto.ResponseUser
import com.nadhifhayazee.domain.usecase.GetUserByIdUseCase
import com.nadhifhayazee.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<ResponseUser?>(null)
    val user get() = _user.asStateFlow()

    init {
        getUser()
    }
    
    fun getUser() {
        viewModelScope.launch {
            getUserByIdUseCase().collectLatest {
                _user.value = it
            }
        }
    }


    fun logout() {
        logoutUseCase()
    }
}