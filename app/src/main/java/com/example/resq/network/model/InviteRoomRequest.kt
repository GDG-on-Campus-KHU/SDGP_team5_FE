package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class InviteRoomRequest(
    @SerializedName("email") val email: String
)