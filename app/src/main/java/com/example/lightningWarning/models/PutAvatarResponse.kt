package com.example.lightningWarning.models

data class PutAvatarResponse(
    val code: Int,
    val data: PutAvatarResponseData,
    val message: String,
    val status: Boolean
)

data class PutAvatarResponseData(
    val avatar: String,
    val company: Company,
    val email: String,
    val full_name: String,
    val id: String,
    val position: String,
    val role: String
)
