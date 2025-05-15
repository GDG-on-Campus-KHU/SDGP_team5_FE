package com.example.resq.presentaion.usermedicalinfo.model

import com.google.gson.annotations.SerializedName

data class TranslateInfo(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("blood_type") val userBloodType: String,
    @SerializedName("allergy") val userAllergy: String,
    @SerializedName("medication") val userMedication: String,
    @SerializedName("height") val userHeight: Double,
    @SerializedName("height_unit") val userHeightUnit: String,
    @SerializedName("weight") val userWeight: Double,
    @SerializedName("weight_unit") val userWeightUnit: String,
    @SerializedName("birth_date") val userBirthdate: String,
    @SerializedName("notes") val userNotes: String,
    @SerializedName("info_titles") val infoTitles: List<String>
)