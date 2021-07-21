package com.example.lightningWarning.models

data class RefreshTokenResponse(
    val code: Int,
    val data: Data,
    val message: String,
    val metadata: Any,
    val status: Boolean
)

data class Data(
    val token: Token
)
