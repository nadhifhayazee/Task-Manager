package com.nadhifhayazee.data.model

import android.net.Uri
import com.google.firebase.firestore.ServerTimestamp
import com.nadhifhayazee.domain.dto.RequestTask
import java.util.Date

data class Task(
    val subject: String,
    val description: String,
    val documentUrl: String,
    val status: String,
    val reminder: Boolean,
    @ServerTimestamp
    val dateTime: Date
) {
    companion object {
        fun mapFromRequestTask(requestTask: RequestTask) : Task {
            return Task(
                requestTask.subject,
                requestTask.description,
                "",
                requestTask.status,
                requestTask.reminder,
                requestTask.dateTime
            )
        }
    }
}