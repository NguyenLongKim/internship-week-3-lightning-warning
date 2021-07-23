package com.example.lightningWarning.models

data class GetMessagesResponse(
    val metadata: MessageMetaData,
    val code: Int,
    val data: List<Message>,
    val message: String,
    val status: Boolean
)

data class Message(
    val created_at: Long,
    val description: String,
    val id: String,
    val title: String
)

data class MessageMetaData(
    val current_page: Int,
    val current_per_page: Int,
    val next_page: Int,
    val prev_page: Int,
    val total_count: Int,
    val total_pages: Int
)