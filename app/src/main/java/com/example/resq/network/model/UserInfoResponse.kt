package com.example.resq.network.model

import com.example.resq.presentaion.user.model.User
import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("data") val userInfo: User,
    @SerializedName("success") val boolean: Boolean
)