package com.nadhifhayazee.domain.dto

import com.google.firebase.firestore.DocumentSnapshot


data class ResponseTask(
    val id: String,
    val subject: String,
    val description: String,
    val status: String,
    val dateTime: String,
    val documentUrl: String
) {
    companion object {
        fun convertFromMap(document: DocumentSnapshot): ResponseTask {
            return ResponseTask(
                document.id,
                document.data?.get("subject").toString(),
                document.data?.get("description").toString(),
                document.data?.get("status").toString(),
                document.data?.get("dateTime").toString(),
                document.data?.get("documentUrl").toString()
            )
        }
    }
}
