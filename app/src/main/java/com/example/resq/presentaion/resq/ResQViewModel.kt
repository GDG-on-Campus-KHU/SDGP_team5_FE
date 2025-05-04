package com.example.resq.presentaion.resq

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.MainActivity.Companion.FAVORITE_RESQ_LIST
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.resq.model.ResQ
import com.example.resq.R
import kotlinx.coroutines.launch

class ResQViewModel : ViewModel() {

    fun getResQList(language: String): List<ResQ> {
        return if (language == "ko")
            listOf(
                ResQ("의식장애/심정지", "cardiac-arrest", R.drawable.resq4),
                ResQ("호흡곤란", "breathing-difficulty", R.drawable.resq3),
                ResQ("출혈", "bleeding", R.drawable.resq2),
                ResQ("외상", "trauma-injury", R.drawable.resq1),
                ResQ("경련/발작", "seizure", R.drawable.resq7),
                ResQ("화상", "burns", R.drawable.resq8),
                ResQ("온열/한랭", "heat-illness/cold-illness", R.drawable.resq6),
                ResQ("정신적응급", "mental-crisis", R.drawable.resq5)
            )
        else
            listOf(
                ResQ("cardiac-arrest", "cardiac-arrest", R.drawable.resq4),
                ResQ("breathing-difficulty", "breathing-difficulty", R.drawable.resq3),
                ResQ("bleeding", "bleeding", R.drawable.resq2),
                ResQ("trauma-injury", "trauma-injury", R.drawable.resq1),
                ResQ("burns", "seizure", R.drawable.resq7),
                ResQ("seizure", "burns", R.drawable.resq8),
                ResQ("heat-illness/cold-illness", "heat-illness/cold-illness", R.drawable.resq6),
                ResQ("mental-crisis", "mental-crisis", R.drawable.resq5)
            )
    }

    fun getEmerNumber(): String {
        return try {
            // 응급 전화 return
            "tel:" + "" // ex)119
        } catch (e: Exception) {
            Log.d("getEmerNumber", e.message.toString())
            ""
        }
    }

    fun getFavoriteResQList() {
        viewModelScope.launch {
            try {
                val response = apiService.getFavoriteResQList().body()
                response?.let { FAVORITE_RESQ_LIST = it.favoriteResQList }
            } catch (e: Exception) {
                Log.d("getFavoriteResQList", e.message.toString())
            }
        }
    }
}