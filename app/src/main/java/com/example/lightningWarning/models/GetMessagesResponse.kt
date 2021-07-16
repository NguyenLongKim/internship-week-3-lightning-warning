package com.example.lightningWarning.models

data class GetMessagesResponse(
    val code: Int,
    val data: List<Message>,
    val message: String,
    val status: Boolean
)

data class Message(
    val time:String,
    val title:String,
    val message: String
)