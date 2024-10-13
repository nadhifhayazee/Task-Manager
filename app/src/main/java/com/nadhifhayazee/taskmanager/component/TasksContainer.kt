package com.nadhifhayazee.taskmanager.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nadhifhayazee.taskmanager.model.TaskStatus
import com.nadhifhayazee.taskmanager.ui.theme.AppTheme

@Composable
fun TaskContainer(modifier: Modifier = Modifier, status: TaskStatus, content: @Composable (()->Unit)) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerLow, shape = RoundedCornerShape(
                    12.dp
                )
            ),
    ) {
        Row (modifier = modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(12.dp))){
            Text(
                text = status.displayName + " Task",
                color = status.color,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = status.color.copy(alpha = 0.15f), shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }

        content()

    }
}

@Preview(showBackground = true)
@Composable
private fun TaskContainerPrev() {
    AppTheme {
        TaskContainer(status = TaskStatus.PENDING) {

        }
    }
}