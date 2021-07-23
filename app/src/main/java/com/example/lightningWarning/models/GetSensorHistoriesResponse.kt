package com.example.lightningWarning.models

data class GetSensorHistoriesResponse(
    val code: Int,
    val data: List<SensorHistory>,
    val message: String,
    val status: Boolean
)

data class SensorHistory(
    val time: String,
    val alarm: String,
    val message: String
)

