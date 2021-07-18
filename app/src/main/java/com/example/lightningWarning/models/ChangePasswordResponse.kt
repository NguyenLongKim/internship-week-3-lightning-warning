package com.example.lightningWarning.models

data class ChangePasswordResponse(
    val code: Int,
    val message: String,
    val status: Boolean
)