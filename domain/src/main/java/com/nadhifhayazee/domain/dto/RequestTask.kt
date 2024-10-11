package com.nadhifhayazee.domain.dto

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class RequestTask(
    val subject: String,
    val description: String,
    val documentUrl: String,
    val status: String,
    val reminder: Boolean,

    @ServerTimestamp
    val dateTime: Date
)