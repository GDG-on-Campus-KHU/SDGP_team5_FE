package com.example.resq.presentaion.resqdetail.model

import com.google.gson.annotations.SerializedName

data class ResQDescriptionDetail(
    @SerializedName("ko") val korean: List<String>
)