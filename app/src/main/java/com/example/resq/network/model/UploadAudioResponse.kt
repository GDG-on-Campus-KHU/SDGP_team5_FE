package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class UploadAudioResponse (
    @SerializedName("data") val response: AudioResponse,
    @SerializedName("success") val boolean: Boolean
)