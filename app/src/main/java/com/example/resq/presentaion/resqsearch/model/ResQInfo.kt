package com.example.resq.presentaion.resqsearch.model

import com.example.resq.presentaion.resqdetail.model.ResQDetailResponse
import com.google.gson.annotations.SerializedName

data class ResQInfo(
    @SerializedName("_index") val index: String,
    @SerializedName("_type") val type: String,
    @SerializedName("_id") val id: String,
    @SerializedName("_score") val score: Float,
    @SerializedName("_source") val resQDetail: ResQDetailResponse
)