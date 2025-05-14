package com.example.resq.presentaion.sign.model

import com.google.gson.annotations.SerializedName

data class CountryInfo(
    @SerializedName("country_code") val countryCode: String,
    @SerializedName("country_lang") val countryLanguage: String,
    @SerializedName("country_name") val country: String,
    @SerializedName("local_emer_call") val localEmerCall: String,
    @SerializedName("intl_emer_call") val emerCall: String
)