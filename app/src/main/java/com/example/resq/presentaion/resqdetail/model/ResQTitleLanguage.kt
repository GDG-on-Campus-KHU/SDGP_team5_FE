package com.example.resq.presentaion.resqdetail.model

import com.google.gson.annotations.SerializedName

data class ResQTitleLanguage(
    @SerializedName("ko") val korean: String,
    @SerializedName("en") val english: String
) {
    fun getLocalizedTitle(language: String): String? {
        return when (language) {
            "ko" -> korean
            "en" -> english
            else -> null
        }
    }
}