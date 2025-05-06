package com.example.resq.network.model

import com.example.resq.presentaion.resqbookmark.model.ResQBookmark
import com.google.gson.annotations.SerializedName

data class FavoriteResQListResponse(
    @SerializedName("data") val favoriteResQList: List<ResQBookmark>,
    @SerializedName("success") val boolean: Boolean
)