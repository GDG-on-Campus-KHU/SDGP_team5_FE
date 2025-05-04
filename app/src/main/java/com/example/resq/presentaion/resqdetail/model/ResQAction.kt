package com.example.resq.presentaion.resqdetail.model

import com.google.gson.annotations.SerializedName

data class ResQAction(
    @SerializedName("ko") val korean: List<ResQDetail>,
    @SerializedName("en") val english: List<ResQDetail>
) {
    fun getLocalizedTitle(language: String): List<ResQDetail> {
        return when (language) {
            "ko" -> korean
            "en" -> english
            else -> emptyList()
        }
    }
}