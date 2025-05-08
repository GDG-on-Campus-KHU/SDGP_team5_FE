package com.example.resq.network.model

import com.example.resq.presentaion.usermedicalinfo.model.TranslateInfo
import com.google.gson.annotations.SerializedName

data class TranslateInfoResponse(
    @SerializedName("data") val data: TranslateInfo,
    @SerializedName("success") val boolean: Boolean
)