package com.nadhifhayazee.taskmanager.screen.task

import androidx.lifecycle.ViewModel
import com.nadhifhayazee.domain.usecase.GetUserTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getUserTasksUseCase: GetUserTasksUseCase,
): ViewModel() {


}