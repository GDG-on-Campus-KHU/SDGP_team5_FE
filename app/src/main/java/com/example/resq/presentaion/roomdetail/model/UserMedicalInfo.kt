package com.example.resq.presentaion.roomdetail.model

data class UserMedicalInfo(
    val userBloodType: String,
    val userAllergy: String,
    val userMedication: String,
    val userHeight: Double,
    val userHeightUnit: String,
    val userWeight: Double,
    val userWeightUnit: String,
    val userBirthdate: String,
    val userNotes: String
)