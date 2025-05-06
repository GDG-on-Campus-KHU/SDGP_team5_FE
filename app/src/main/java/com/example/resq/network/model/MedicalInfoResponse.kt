package com.example.resq.network.model

import com.example.resq.presentaion.usermedicalinfo.model.MedicalInfoData
import com.google.gson.annotations.SerializedName

data class MedicalInfoResponse (
    @SerializedName("data") val data: MedicalInfoData,
    @SerializedName("success") val boolean: Boolean
)