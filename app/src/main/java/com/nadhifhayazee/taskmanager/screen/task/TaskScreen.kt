package com.nadhifhayazee.taskmanager.screen.task

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nadhifhayazee.taskmanager.component.TaskTile
import com.nadhifhayazee.taskmanager.ui.theme.AppTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel(),
    onNavigateToCreateTask: () -> Unit
) {


    var selectedDateString by remember { mutableStateOf(todayAsString()) }
    var selectedDate by remember { mutableStateOf(Date()) }
    val tasks by viewModel.tasks.collectAsState()

    LaunchedEffect(selectedDate) {
        viewModel.getTasks(selectedDate)
    }

//    LaunchedEffect(Unit) {
//        viewModel.getUrlFile()
//    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        TopAppBar(
            title = { Text("Tasks") },
            actions = {
                // Create Task Button
                TextButton(onClick = {
                    onNavigateToCreateTask()
                }) {
                    Text("Buat Task", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
                }
                // Date Selector Button
                SelectDateButton(selectedDateString) { newDate, date ->
                    selectedDateString = newDate
                    selectedDate = date
                }
            }
        )

        if (tasks.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(vertical = 24.dp, horizontal = 16.dp)
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskTile(
                        task = task,
                        modifier = modifier.padding(vertical = 10.dp)
                    ) { newStatus ->
                        viewModel.updateStatus(task.id, newStatus)
                    }
                }
            }
        } else {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Tidak ada task di tanggal ini.", style = MaterialTheme.typography.bodySmall)
            }
        }


    }
}

@Composable
fun SelectDateButton(selectedDate: String, onDateSelected: (String, Date) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            val newDateStr = String.format("%02d-%02d-%04d", day, month + 1, year)
            val date = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month) // Month is 0-based
                set(Calendar.DAY_OF_MONTH, day)
            }.time  //
            onDateSelected(newDateStr, date)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    OutlinedButton(onClick = { datePicker.show() }, colors = ButtonDefaults.buttonColors()) {
        Text(text = selectedDate)
    }
}

fun todayAsString(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return dateFormat.format(Date())
}

@Preview(showBackground = true)
@Composable
private fun TaskScreenPrev() {
    AppTheme {
        TaskScreen(onNavigateToCreateTask = {})
    }

}