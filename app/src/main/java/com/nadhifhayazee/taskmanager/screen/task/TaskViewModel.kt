package com.nadhifhayazee.taskmanager.screen.task

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhifhayazee.domain.dto.ResponseTask
import com.nadhifhayazee.domain.dto.ResultState
import com.nadhifhayazee.domain.usecase.GetFileUrlUseCase
import com.nadhifhayazee.domain.usecase.GetUserTasksRealtimeUseCase
import com.nadhifhayazee.domain.usecase.GetUserTasksUseCase
import com.nadhifhayazee.domain.usecase.UpdateTaskFieldUseCase
import com.nadhifhayazee.taskmanager.util.DateUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getUserTasksRealtimeUseCase: GetUserTasksRealtimeUseCase,
    private val updateTaskFieldUseCase: UpdateTaskFieldUseCase,
    private val getFileUrlUseCase: GetFileUrlUseCase
): ViewModel() {



    private val _tasks = MutableStateFlow<List<ResponseTask>>(emptyList())
    val tasks get() = _tasks.asStateFlow()

    private val _urlFile = MutableSharedFlow<Uri>()
    val urlFile get() = _urlFile.asSharedFlow()

    fun getTasks(selectedDate: Date) {
        viewModelScope.launch {
            getUserTasksRealtimeUseCase(
                startTime = DateUtil.selectedDateStartTime(selectedDate),
                endTime = DateUtil.selectedDateEndTime(selectedDate),
                orderBy = "dateTime",
                sortType = "DESC"
            ).collectLatest {
                when(it) {
                    is ResultState.Success -> {

                        _tasks.value = it.data
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

    fun getUrlFile() {
        viewModelScope.launch {
            getFileUrlUseCase("dc5a0ff3-44fe-4bd3-9ced-67713541b190","coder.png").collectLatest {
                Log.i("url", it.toString())
            }
        }
    }
}