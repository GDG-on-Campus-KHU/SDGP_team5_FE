package com.example.resq.presentaion.usermedicalinfo.model

import com.google.gson.annotations.SerializedName

data class MedicalInfo(
    @SerializedName("id") val userId: String,
    @SerializedName("info_id") val userInfoId: Int,
    @SerializedName("blood_type") val userBloodType: String,
    @SerializedName("allergy") val userAllergy: String,
    @SerializedName("medication") val userMedication: String,
    @SerializedName("height") val userHeight: Float,
    @SerializedName("height_unit") val userHeightUnit: String,
    @SerializedName("weight") val userWeight: Float,
    @SerializedName("weight_unit") val userWeighUnit: String,
    @SerializedName("birth_date") val userBirthdate: String,
    @SerializedName("notes") val userNotes: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)