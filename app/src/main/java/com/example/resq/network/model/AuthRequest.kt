package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("serverAuthCode") val serverAuthCode: String
)