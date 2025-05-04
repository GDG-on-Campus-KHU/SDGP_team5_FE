package com.example.resq.presentaion.resqdetail.model

import com.google.gson.annotations.SerializedName

data class ResQDescriptionDetail(
    @SerializedName("ko") val korean: List<String>,
    @SerializedName("en") val english: List<String>
) {
    fun getLocalizedTitle(language: String): List<String> {
        return when (language) {
            "ko" -> korean
            "en" -> english
            else -> emptyList()
        }
    }
}