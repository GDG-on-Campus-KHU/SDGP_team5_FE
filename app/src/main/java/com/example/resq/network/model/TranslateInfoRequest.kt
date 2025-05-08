package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class TranslateInfoRequest(
    @SerializedName("user_id") val userId: Int
)