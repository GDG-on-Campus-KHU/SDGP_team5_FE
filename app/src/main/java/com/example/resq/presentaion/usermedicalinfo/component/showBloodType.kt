package com.example.resq.presentaion.usermedicalinfo.component

import com.example.resq.presentaion.usermedicalinfo.model.MedicalInfoElement

fun showBloodType(element: MedicalInfoElement): String {
    val value = element.value.value ?: return element.placeholder
    return when {
        value.endsWith("+") -> "RH+ ${value.dropLast(1)}"
        value.endsWith("-") -> "RH- ${value.dropLast(1)}"
        else -> value
    }
}