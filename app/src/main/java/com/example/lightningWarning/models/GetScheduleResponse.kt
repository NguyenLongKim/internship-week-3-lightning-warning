package com.example.lightningWarning.models

data class GetScheduleResponse(
    val code: Int,
    val data: ScheduleData,
    val message: String,
    val status: Boolean
)

data class ScheduleData(
    val alarm_sound: Boolean,
    val alarm_vibration: Boolean,
    val do_not_disturb: Boolean,
    val end_hour: Int,
    val end_min: Int,
    val friday: Boolean,
    val monday: Boolean,
    val saturday: Boolean,
    val start_hour: Int,
    val start_min: Int,
    val sunday: Boolean,
    val thursday: Boolean,
    val tuesday: Boolean,
    val updated_at: Int,
    val wednesday: Boolean
)