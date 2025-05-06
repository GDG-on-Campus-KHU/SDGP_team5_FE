package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class Language(
    @SerializedName("ko") val korean: String,
    @SerializedName("ko") val english: String
)