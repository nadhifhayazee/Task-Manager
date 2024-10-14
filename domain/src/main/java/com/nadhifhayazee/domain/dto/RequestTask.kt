package com.nadhifhayazee.domain.dto

import android.net.Uri
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class RequestTask(
    val subject: String,
    val description: String,
    val documentUri: Uri?,
    val status: String,
    val reminder: Boolean,

    @ServerTimestamp
    val dateTime: Date
)