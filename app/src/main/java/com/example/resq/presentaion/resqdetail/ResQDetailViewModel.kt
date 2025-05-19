package com.example.resq.presentaion.resqdetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.MainActivity.Companion.FAVORITE_RESQ_LIST
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.presentaion.resqdetail.model.ResQDetailResponse
import com.example.resq.presentaion.sign.GoogleSignViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ResQDetailViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _resQDetail = MutableStateFlow(emptyList<ResQDetailResponse>())
    val resQDetail: MutableStateFlow<List<ResQDetailResponse>> = _resQDetail

    fun getResQDetail(
        resQ: String,
        language: String,
        onResQDetail: (List<ResQDetailResponse>) -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if (resQ == "heat-illness/cold-illness")
                    listOf("heat-illness", "cold-illness").forEach { resQ ->
                        val response = apiService.getResQDetail(resQ, language)
                        response.body()?.let { _resQDetail.value += it }
                    }
                else {
                    val response = apiService.getResQDetail(resQ, language)
                    response.body()?.let { _resQDetail.value += it }
                }
            } catch (e: Exception) {
                Log.d("getResQDetail", e.message.toString())
            }
            onResQDetail(_resQDetail.value)
            _isLoading.value = false
        }
    }

    fun addToFavoriteResQList(resQ: Int) {
        viewModelScope.launch {
            try {
                apiService.addToFavoriteResQList(resQ)
                GoogleSignViewModel().getFavoriteResQList { FAVORITE_RESQ_LIST = it }
            } catch (e: Exception) {
                Log.d("addToFavoriteResQList", e.message.toString())
            }
        }
    }

    fun deleteToFavoriteResQList(resQ: Int) {
        viewModelScope.launch {
            try {
                apiService.deleteToFavoriteResQList(resQ)
                GoogleSignViewModel().getFavoriteResQList { FAVORITE_RESQ_LIST = it }
            } catch (e: Exception) {
                Log.d("deleteToFavoriteResQList", e.message.toString())
            }
        }
    }
}