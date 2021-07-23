package com.example.lightningWarning.utils

import com.example.lightningWarning.models.ErrorResponse
import com.google.gson.Gson
import okhttp3.ResponseBody

class ErrorUtil {
    companion object {
        private val gson = Gson()
        fun parseErrorBody(errorBody: ResponseBody): ErrorResponse {
            return gson.fromJson(
                errorBody.charStream(),
                ErrorResponse::class.java
            )
        }
    }
}