package com.example.lightningWarning.models

data class GetSensorsResponse(
    val code: Int,
    val data: List<SensorData>,
    val message: String,
    val metadata: Metadata,
    val status: Boolean
)

data class SensorData(
    val alarm: String,
    val display_name: String,
    val id: String
)

data class Metadata(
    val current_page: Int,
    val current_per_page: Int,
    val next_page: Int,
    val prev_page: Int,
    val total_count: Int,
    val total_pages: Int
)
