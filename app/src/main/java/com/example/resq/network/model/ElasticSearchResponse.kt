package com.example.resq.network.model

import com.example.resq.presentaion.resqsearch.model.ResQInfo
import com.google.gson.annotations.SerializedName

data class ElasticSearchResponse(
    @SerializedName("hits") val hits: HitsWrapper
)

data class HitsWrapper(
    @SerializedName("total") val total: TotalCount,
    @SerializedName("max_score") val maxScore: Float,
    @SerializedName("hits") val hits: List<ResQInfo>
)

data class TotalCount(
    @SerializedName("value") val value: Int,
    @SerializedName("relation") val relation: String
)