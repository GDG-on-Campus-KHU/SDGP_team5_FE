package com.example.resq.network.model

import com.example.resq.presentaion.rooms.model.Room
import com.google.gson.annotations.SerializedName

data class NewRoomResponse(
    @SerializedName("data") val data: Room,
    @SerializedName("success") val boolean: Boolean
)
