package com.nadhifhayazee.taskmanager.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadhifhayazee.taskmanager.model.TaskStatus
import com.nadhifhayazee.taskmanager.ui.theme.AppTheme

@Composable
fun TaskStatusCard(
    modifier: Modifier = Modifier,
    status: TaskStatus,
    totalCount: Int
) {
    Card(
        modifier = modifier
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = status.color.copy(alpha = 0.15f))// Lighter background
    ) {
        Row(
            modifier = modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = status.name.replace("_", " "), // Display status name
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = status.color
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$totalCount Tasks",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun TaskStatusCardPrev() {
    AppTheme {
        TaskStatusCard(status = TaskStatus.DONE, totalCount = 2)
    }

}