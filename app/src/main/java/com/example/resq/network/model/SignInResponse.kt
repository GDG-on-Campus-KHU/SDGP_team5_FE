package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("token") val accessToken: String
)