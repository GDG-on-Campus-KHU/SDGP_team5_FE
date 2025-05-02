package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class FavoriteResQListResponse(
    @SerializedName("data") val favoriteResQList: List<String>,
    @SerializedName("success") val boolean: Boolean
)