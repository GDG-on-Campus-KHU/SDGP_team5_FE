package com.example.resq.presentaion.resqbookmark.model

import com.example.resq.network.model.Language
import com.google.gson.annotations.SerializedName

data class ResQBookmark(
    @SerializedName("SituationIndex") val resQIndex: Int,
    @SerializedName("Situation") val resQTitle: Language,
    @SerializedName("Slug") val resQSlug: String
)