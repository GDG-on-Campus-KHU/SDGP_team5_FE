package com.example.resq.presentaion.usersetting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.network.model.CountryRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserSettingViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun updateCountry(request: CountryRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = apiService.updateCountry(request)
                val success = response.isSuccessful && response.body()?.boolean == true
                if (success) {
                    Log.d("updateCountry", "국가 코드 변경 성공")
                } else {
                    Log.e("updateCountry", "국가 코드 변경 실패: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("updateCountry", "예외 발생: ${e.message}")
            }
            _isLoading.value = false
        }
    }
}
