package com.example.resq.presentaion.usermedicalinfo.component

import com.example.resq.presentaion.usermedicalinfo.model.MedicalInfoElement

fun showUnit(element: MedicalInfoElement): String {
    val value = element.value.value
    return if (value.isNullOrBlank() || value == "0.0") {
        element.placeholder
    } else {
        "$value ${element.unit ?: ""}"
    }
}