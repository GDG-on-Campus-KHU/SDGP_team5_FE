package com.example.resq.presentaion.resqdetail.model

import com.google.gson.annotations.SerializedName

data class ResQDetailResponse(
    @SerializedName("index") val resQIndex: Int?,
    @SerializedName("slug") val slug: String?,
    @SerializedName("emoji") val resQEmoji: String?,
    @SerializedName("emer_title") val resQTitle: ResQTitleLanguage?,
    @SerializedName("description") val description: ResQDescriptionDetail?,
    @SerializedName("actions") val resQActions: ResQAction?,
)