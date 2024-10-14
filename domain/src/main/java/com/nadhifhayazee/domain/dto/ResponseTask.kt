package com.nadhifhayazee.domain.dto

import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date


data class ResponseTask(
    val id: String,
    val subject: String,
    val description: String,
    val status: String,
    val dateTime: Date?,
    val documentUrl: String
) {
    companion object {
        fun convertFromMap(document: DocumentSnapshot): ResponseTask {
            return ResponseTask(
                document.id,
                document.data?.get("subject").toString(),
                document.data?.get("description").toString(),
                document.data?.get("status").toString(),
                document.getTimestamp("dateTime")?.toDate(),
                document.data?.get("documentUrl").toString()
            )
        }
    }
}
