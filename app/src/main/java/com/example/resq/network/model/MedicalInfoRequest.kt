package com.example.resq.network.model

import com.google.gson.annotations.SerializedName

data class MedicalInfoRequest(
    @SerializedName("blood_type") val bloodType: String,
    @SerializedName("allergy") val allergy: String,
    @SerializedName("medication") val medication: String,
    @SerializedName("height") val height: Double,
    @SerializedName("height_unit") val heightUnit: String,
    @SerializedName("weight") val weight: Double,
    @SerializedName("weight_unit") val weightUnit: String,
    @SerializedName("birth_date") val birthDate: String,
    @SerializedName("notes") val notes: String
)