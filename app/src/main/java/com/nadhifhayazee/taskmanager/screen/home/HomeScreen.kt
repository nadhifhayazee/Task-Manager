package com.nadhifhayazee.taskmanager.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nadhifhayazee.taskmanager.component.TaskContainer
import com.nadhifhayazee.taskmanager.component.TaskStatusCard
import com.nadhifhayazee.taskmanager.component.TaskTile
import com.nadhifhayazee.taskmanager.model.TaskStatus
import com.nadhifhayazee.taskmanager.ui.theme.AppTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {

    val pendingTasks by viewModel.pendingTasks.collectAsState()

    val inProgressTasks by viewModel.inProgressTasks.collectAsState()

    val doneTasks by viewModel.doneTasks.collectAsState()

    Column(
        modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                TaskStatusCard(
                    status = TaskStatus.PENDING,
                    totalCount = pendingTasks.size
                )
                TaskStatusCard(
                    modifier = Modifier.fillMaxWidth(1f),
                    status = TaskStatus.IN_PROGRESS,
                    totalCount = inProgressTasks.size
                )


            }
            TaskStatusCard(
                modifier = Modifier.fillMaxWidth(),
                status = TaskStatus.DONE,
                totalCount = doneTasks.size
            )
        }


        TaskContainer(status = TaskStatus.PENDING) {
            if (pendingTasks.isNotEmpty()) {
                pendingTasks.forEach { task->
                    TaskTile(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        task = task,
                        onStatusChange = { newStatus ->
                            viewModel.updateStatus(task.id, newStatus)
                        }
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Tidak ada Task Pending.")
                }
            }

        }

        Spacer(modifier = Modifier.height(24.dp))

        TaskContainer(status = TaskStatus.IN_PROGRESS) {
            if (inProgressTasks.isNotEmpty()) {
                inProgressTasks.forEach { task ->
                    TaskTile(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        task = task,
                        onStatusChange = { newStatus ->
                            viewModel.updateStatus(task.id, newStatus)
                        }
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Tidak ada Task In Progress.")
                }
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPrev() {
    AppTheme {
        HomeScreen()
    }
}