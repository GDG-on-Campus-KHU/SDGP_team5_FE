package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class NewTokenRequest(
    @SerializedName("refresh_token") val accessToken: String
)