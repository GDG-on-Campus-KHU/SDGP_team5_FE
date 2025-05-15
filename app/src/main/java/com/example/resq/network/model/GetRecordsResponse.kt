package com.example.resq.network.model

import com.example.resq.presentaion.userrecordlist.model.RecordInfo
import com.google.gson.annotations.SerializedName

data class GetRecordsResponse(
    @SerializedName("data") val records: List<RecordInfo>,
    @SerializedName("success") val boolean: Boolean
)