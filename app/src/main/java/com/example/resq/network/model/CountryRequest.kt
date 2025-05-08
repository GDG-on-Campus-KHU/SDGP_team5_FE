package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class CountryRequest (
    @SerializedName("country_code") val countryCode: String
)