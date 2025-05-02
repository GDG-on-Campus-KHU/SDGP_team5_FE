package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    @SerializedName("data") val responseMessage: String,
    @SerializedName("success") val boolean: Boolean
)