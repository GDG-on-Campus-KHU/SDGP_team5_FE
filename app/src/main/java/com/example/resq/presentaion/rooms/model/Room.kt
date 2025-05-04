package com.example.resq.presentaion.rooms.model

import com.google.gson.annotations.SerializedName

data class Room(
    @SerializedName("id") val roomId: String,
    @SerializedName("group_name") val roomTitle: String,
    @SerializedName("members") val roomMembers: List<Member>,
    @SerializedName("created_at") val createdAt: String
)