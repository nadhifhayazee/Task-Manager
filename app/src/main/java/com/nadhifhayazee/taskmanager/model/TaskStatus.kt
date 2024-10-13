package com.nadhifhayazee.taskmanager.model

import androidx.compose.ui.graphics.Color

enum class TaskStatus(val color: Color, val displayName: String) {
    PENDING(Color(0xFFFFC107), "Pending"),      // Amber
    IN_PROGRESS(Color(0xFF2196F3), "In Progress"),  // Blue
    DONE(Color(0xFF4CAF50), "Done")          // Green
}