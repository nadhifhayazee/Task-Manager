package com.nadhifhayazee.taskmanager.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadhifhayazee.domain.dto.ResponseTask
import com.nadhifhayazee.taskmanager.model.TaskStatus
import com.nadhifhayazee.taskmanager.ui.theme.AppTheme
import com.nadhifhayazee.taskmanager.util.DateUtil
import java.util.Date

@Composable
fun TaskTile(modifier: Modifier = Modifier, task: ResponseTask, onStatusChange: (String) -> Unit) {
    var selectedStatus by remember { mutableStateOf(task.status) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(12.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Row for Subject and DateTime
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LabeledText(
                    label = "Subject",
                    value = task.subject,
                    modifier = Modifier.weight(1f)
                )
                LabeledText(
                    label = "Date & Time",
                    value = if (task.dateTime != null) DateUtil.formatDate(task.dateTime!!) else "-",
                    modifier = Modifier.weight(1f)
                )

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {


                LabeledText(
                    label = "Description",
                    value = task.description,
                    modifier = Modifier.weight(1f)
                )
                // Status Dropdown
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    TaskDropdownStatus(
                        selectedStatus = if (selectedStatus == TaskStatus.PENDING.name) TaskStatus.PENDING else if (selectedStatus == TaskStatus.IN_PROGRESS.name) TaskStatus.IN_PROGRESS else TaskStatus.DONE,
                        onStatusChange = { newStatus ->
                            selectedStatus = newStatus.name
                            onStatusChange(newStatus.name)
                        }
                    )
                }
            }

            // Row for Document/Photo Thumbnail
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Document/Photo",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(end = 8.dp)
                )
                if (task.documentUrl.isNotEmpty()) {
                    FileImageView(task.documentUrl)
                } else {
                    Text(
                        text = "No Document",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
fun LabeledText(
    label: String, value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
fun TaskDropdownStatus(
    selectedStatus: TaskStatus,
    onStatusChange: (TaskStatus) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors()
                .copy(containerColor = selectedStatus.color.copy(alpha = 0.7f))
        ) {
            Text(
                text = selectedStatus.displayName,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            TaskStatus.entries.forEach { status ->
                DropdownMenuItem(
                    text = { Text(status.displayName) },
                    onClick = {
                        onStatusChange(status)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskTilePrev() {
    AppTheme {
        TaskTile(
            task = ResponseTask(
                "ss",
                "Beli bakso",
                "di warung pojok",
                TaskStatus.PENDING.name,
                Date(),
                ""
            ),
            onStatusChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LabeledTextPrev() {
    AppTheme {
        LabeledText("Subject", "Beli bakso")
    }

}

@Preview(showBackground = true)
@Composable
private fun DropdownPrev() {
    AppTheme {
        TaskDropdownStatus(TaskStatus.PENDING) { }
    }

}
