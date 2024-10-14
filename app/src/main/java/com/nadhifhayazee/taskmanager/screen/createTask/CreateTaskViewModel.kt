package com.nadhifhayazee.taskmanager.screen.createTask

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhifhayazee.domain.dto.RequestTask
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.usecase.AddUserTaskUseCase
import com.nadhifhayazee.taskmanager.model.TaskStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val addUserTaskUseCase: AddUserTaskUseCase,

    ) : ViewModel() {

    private val _addState = MutableSharedFlow<ResultState<Boolean>>()
    val addState get() = _addState.asSharedFlow()

    fun addTask(subject: String, description: String, date: Date, document: Uri?) {
        viewModelScope.launch {
            val request = RequestTask(
                subject = subject,
                description = description,
                documentUri = document,
                status = TaskStatus.PENDING.name,
                reminder = true,
                dateTime = date
            )
            addUserTaskUseCase(request).collectLatest {
                _addState.emit(it)
            }
        }
    }
}