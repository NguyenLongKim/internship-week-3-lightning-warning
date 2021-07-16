package com.example.lightningWarning.models

data class GetAlertsResponse(
    val code: Int,
    val data: List<Alert>,
    val message: String,
    val status: Boolean
)

data class Alert(
    val time:String,
    val title:String,
    val message: String
)