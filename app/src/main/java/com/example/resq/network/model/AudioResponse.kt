package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class AudioResponse(
    @SerializedName("recording_url") val recordUrl: String,
    @SerializedName("transcription") val recordTranscription: String
)