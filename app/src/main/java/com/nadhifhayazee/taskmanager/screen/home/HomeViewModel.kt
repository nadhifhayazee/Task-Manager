package com.nadhifhayazee.taskmanager.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhifhayazee.domain.dto.ResponseTask
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.usecase.GetUserTasksRealtimeUseCase
import com.nadhifhayazee.domain.usecase.UpdateTaskFieldUseCase
import com.nadhifhayazee.taskmanager.model.TaskStatus
import com.nadhifhayazee.taskmanager.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
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

    private val _pendingTaskCount = MutableStateFlow<Int>(0)
    val pendingTaskCount get() = _pendingTaskCount.asStateFlow()

    private val _inProgressTaskCount = MutableStateFlow<Int>(0)
    val inProgressTaskCount get() = _inProgressTaskCount.asStateFlow()

    private val _doneTaskCount = MutableStateFlow<Int>(0)
    val doneTaskCount get() = _doneTaskCount.asStateFlow()

    private val todayDate = Date()

    init {
        viewModelScope.launch {
            getUserTasksRealtimeUseCase(
                status = TaskStatus.PENDING.name,
                startTime = DateUtil.selectedDateStartTime(todayDate),
                endTime = DateUtil.getEndOfCurrentMonth(),
                orderBy = "dateTime",
                sortType = "ASC",
                limit = 3
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
            getUserTasksRealtimeUseCase(
                status = TaskStatus.IN_PROGRESS.name,
                startTime = DateUtil.selectedDateStartTime(todayDate),
                endTime = DateUtil.getEndOfCurrentMonth(),
                orderBy = "dateTime",
                sortType = "ASC",
                limit = 3
            ).collectLatest {
                when (it) {
                    is ResultState.Success -> {
                        _inProgressTasks.value = it.data
                    }

                    else -> {}
                }

            }
        }

        viewModelScope.launch {
            getUserTasksRealtimeUseCase().collectLatest {
                when (it) {
                    is ResultState.Success -> {
                        val pending = it.data.filter { it.status == TaskStatus.PENDING.name }.size
                        val inProgress =
                            it.data.filter { it.status == TaskStatus.IN_PROGRESS.name }.size
                        val done = it.data.filter { it.status == TaskStatus.DONE.name }.size
                        _pendingTaskCount.value = pending
                        _inProgressTaskCount.value = inProgress
                        _doneTaskCount.value = done
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