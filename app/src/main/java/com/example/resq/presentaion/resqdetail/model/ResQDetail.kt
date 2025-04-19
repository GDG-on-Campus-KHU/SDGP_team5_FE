package com.example.resq.presentaion.resqdetail.model

import com.google.gson.annotations.SerializedName

data class ResQDetail(
    @SerializedName("step") val step: String,
    @SerializedName("details") val detail: List<String>
)