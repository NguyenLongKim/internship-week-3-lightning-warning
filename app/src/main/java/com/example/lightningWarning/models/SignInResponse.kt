package com.example.lightningWarning.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class SignInResponse(
    val code: Int,
    val data: UserData?,
    val message: String,
    val status: Boolean
)

@Parcelize
data class UserData(
    val company: Company,
    val email: String,
    val full_name: String,
    val id: String,
    val position: String,
    val role: String,
    val token: Token
):Parcelable

@Parcelize
data class Company(
    val full_name: String,
    val id: String
):Parcelable

@Parcelize
data class Token(
    val expired_at: Int,
    val refresh_token: String,
    val token: String
):Parcelable