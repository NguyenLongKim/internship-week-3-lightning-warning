package com.example.lightningWarning.models

data class GetSensorDetailResponse(
    val code: Int,
    val data: SensorDetailData,
    val message: String,
    val status: Boolean
)

data class SensorDetailData(
    val alarm: String,
    val display_name: String,
    val id: String,
    val installation_address: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val note: String,
    val subscription_end_date: String,
    val subscription_start_date: String,
    val z1r: Int,
    val z2r: Int
)