package com.example.resq.network.model

import com.example.resq.presentaion.sign.model.CountryInfo
import com.google.gson.annotations.SerializedName

data class CountryInfoResponse(
    @SerializedName("data") val countryInfo: CountryInfo,
    @SerializedName("success") val boolean: Boolean
)