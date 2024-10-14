package com.nadhifhayazee.taskmanager.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhifhayazee.domain.dto.ResponseTask
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.usecase.GetUserTasksRealtimeUseCase
import com.nadhifhayazee.domain.usecase.UpdateTaskFieldUseCase
import com.nadhifhayazee.taskmanager.model.TaskStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserTasksRealtimeUseCase: GetUserTasksRealtimeUseCase,
    private val updateTaskFieldUseCase: UpdateTaskFieldUseCase,
) : ViewModel() {

    private val _pendingTasks = MutableStateFlow<List<ResponseTask>>(emptyList())
    val pendingTasks get() = _pendingTasks.asStateFlow()

    private val _inProgressTasks = MutableStateFlow<List<ResponseTask>>(emptyList())
    val inProgressTasks get() = _inProgressTasks.asStateFlow()

    private val _doneTasks = MutableStateFlow<List<ResponseTask>>(emptyList())
    val doneTasks get() = _doneTasks.asStateFlow()

    init {
        viewModelScope.launch {
            getUserTasksRealtimeUseCase(
                status = TaskStatus.PENDING.name,
                orderBy = "dateTime",
                sortType = "DESC"
            ).collectLatest {
                when (it) {
                    is ResultState.Success -> {
                        _pendingTasks.value = it.data

                    }

                    else -> {}
                }
            }
        }
        viewModelScope.launch {
            getUserTasksRealtimeUseCase(status = TaskStatus.IN_PROGRESS.name).collectLatest {
                when (it) {
                    is ResultState.Success -> {
                        _inProgressTasks.value = it.data
                    }

                    else -> {}
                }

            }
        }
        viewModelScope.launch {
            getUserTasksRealtimeUseCase(status = TaskStatus.DONE.name).collectLatest {
                when (it) {
                    is ResultState.Success -> {
                        _doneTasks.value = it.data
                    }

                    else -> {}
                }
            }
        }

    }

    fun updateStatus(taskId: String, newStatus: String) {
        viewModelScope.launch {
            updateTaskFieldUseCase(taskId, "status", newStatus).collectLatest {

            }
        }
    }
}