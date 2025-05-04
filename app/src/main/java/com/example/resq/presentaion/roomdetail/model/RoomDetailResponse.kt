package com.example.resq.presentaion.roomdetail.model

import com.example.resq.presentaion.rooms.model.Room
import com.google.gson.annotations.SerializedName

data class RoomDetailResponse(
    @SerializedName("data") val roomDetail: Room,
    @SerializedName("success") val boolean: Boolean
)