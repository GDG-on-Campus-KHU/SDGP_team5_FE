package com.example.resq.presentaion.resqsearch.model

import com.google.gson.annotations.SerializedName

data class SearchRequest(
    @SerializedName("query") val query: QueryWrapper
)

data class QueryWrapper(
    @SerializedName("match") val match: MatchQuery
)

data class MatchQuery(
    @SerializedName("actions.en.step") val resQDetail: MatchDetail
)

data class MatchDetail(
    @SerializedName("query") val query: String,
    @SerializedName("fuzziness") val fuzziness: String = "AUTO"
)