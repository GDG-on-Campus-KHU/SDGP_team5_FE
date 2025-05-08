package com.example.resq.presentaion.userrecordlist.model

import com.google.gson.annotations.SerializedName

data class RecordInfo(
    @SerializedName("id") val id: String,
    @SerializedName("recording_id") val recordedId: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("recording_url") val recordUrl: String,
    @SerializedName("recording_text") val recordText: String,
    @SerializedName("app_lang") val appLanguage: String,
    @SerializedName("created_at") val createdAt: String
)