package com.example.resq.presentaion.rooms.model

import com.google.gson.annotations.SerializedName

data class Member(
    @SerializedName("user_id") val userId: String,
    @SerializedName("email") val userEmail: String,
    @SerializedName("status") val invitedStatus: String,
    @SerializedName("invited_by") val invitedBy: Int
)
