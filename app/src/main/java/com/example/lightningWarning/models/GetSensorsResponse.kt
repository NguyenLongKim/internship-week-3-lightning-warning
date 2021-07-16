package com.example.lightningWarning.models

data class GetSensorsResponse(
    val code: Int,
    val data: List<SensorData>,
    val message: String,
    val status: Boolean
)

data class SensorData(
    val alarm: String,
    val display_name: String,
    val id: String
)

