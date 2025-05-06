package com.example.resq.presentaion.user.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("UserID") val userId: Int,
    @SerializedName("CountryCode") val userCountryCode: String,
    @SerializedName("Name") val userName: String,
    @SerializedName("Email") val userEmail: String,
    @SerializedName("AppLang") val appLanguage: String
)
