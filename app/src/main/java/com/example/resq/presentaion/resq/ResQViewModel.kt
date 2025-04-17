package com.example.resq.presentaion.resq

import androidx.lifecycle.ViewModel
import com.example.resq.R
import com.example.resq.presentaion.resq.model.ResQ

class ResQViewModel : ViewModel() {

    fun getResQList(language: String): List<ResQ> {
        return if (language == "ko")
            listOf(
                ResQ("의식장애/심정지", R.drawable.resq4),
                ResQ("호흡곤란", R.drawable.resq3),
                ResQ("출혈", R.drawable.resq2),
                ResQ("외상", R.drawable.resq1),
                ResQ("경련/발작", R.drawable.resq7),
                ResQ("화상", R.drawable.resq8),
                ResQ("온열/한랭", R.drawable.resq6),
                ResQ("정신적응급", R.drawable.resq5)
            )
        else
            listOf(
                ResQ("ConsciousnessDisorder/CardiacArrest", R.drawable.resq4),
                ResQ("Dyspnea", R.drawable.resq3),
                ResQ("Bleeding", R.drawable.resq2),
                ResQ("Trauma", R.drawable.resq1),
                ResQ("Seizure/Convulsion", R.drawable.resq7),
                ResQ("Burns", R.drawable.resq8),
                ResQ("Heat/ColdStress", R.drawable.resq6),
                ResQ("MentalHealthEmergency", R.drawable.resq5)
            )
    }

}