package com.example.lightningWarning.models

data class PutScheduleResponse(
    val code: Int,
    val data: ScheduleData,
    val message: String,
    val status: Boolean
)

data class RequestUpdateScheduleData(
    var alarm_sound: Boolean? = null,
    var alarm_vibration: Boolean? = null,
    var do_not_disturb: Boolean? = null,
    var end_hour: Int? = null,
    var end_min: Int? = null,
    var friday: Boolean? = null,
    var monday: Boolean? = null,
    var saturday: Boolean? = null,
    var start_hour: Int? = null,
    var start_min: Int? = null,
    var sunday: Boolean? = null,
    var thursday: Boolean? = null,
    var tuesday: Boolean? = null,
    var wednesday: Boolean? = null
)

