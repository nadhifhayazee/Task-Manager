package com.nadhifhayazee.domain.dto


data class ResponseTask(
    val subject: String,
    val description: String,
    val status: String,
    val dateTime: String,
    val documentUrl: String
) {
    companion object {
        fun convertFromMap(data: Map<String, Any>): ResponseTask {
            return ResponseTask(
                data["subject"].toString(),
                data["description"].toString(),
                data["status"].toString(),
                data["dateTime"].toString(),
                data["documentUrl"].toString()
            )
        }
    }
}
