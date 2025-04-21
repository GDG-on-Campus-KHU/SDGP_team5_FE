package com.example.resq.presentaion.resqdetail.model

import com.google.gson.annotations.SerializedName

data class ResQAction(
    @SerializedName("ko") val resQDetail: List<ResQDetail>
)