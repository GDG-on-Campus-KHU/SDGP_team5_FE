package com.example.resq.presentaion.usersetting

import android.content.res.Configuration
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.MainActivity.Companion.appContext
import com.example.resq.R
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.network.model.CountryRequest
import com.example.resq.presentaion.usersetting.model.Country
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class UserSettingViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _countries = MutableStateFlow(
        listOf(
            Country("KR", appContext.getString(R.string.country_kr)),
            Country("US", appContext.getString(R.string.country_us)),
            Country("GB", appContext.getString(R.string.country_gb)),
            Country("JP", appContext.getString(R.string.country_jp)),
            Country("CN", appContext.getString(R.string.country_cn)),
            Country("DE", appContext.getString(R.string.country_de)),
            Country("FR", appContext.getString(R.string.country_fr)),
            Country("MX", appContext.getString(R.string.country_mx))
        )
    )
    val countries: StateFlow<List<Country>> = _countries

    private val _languages = MutableStateFlow(
        listOf(
            Country("ko", appContext.getString(R.string.korean)),
            Country("en", appContext.getString(R.string.english)),
        )
    )
    val languages: StateFlow<List<Country>> = _languages

    fun updateTranslation(appLanguage: String) {
        val locale = Locale(appLanguage)
        val config = Configuration(appContext.resources.configuration)
        config.setLocale(locale)
        val context = appContext.createConfigurationContext(config)

        _countries.update {
            listOf(
                Country("KR", context.getString(R.string.country_kr)),
                Country("US", context.getString(R.string.country_us)),
                Country("GB", context.getString(R.string.country_gb)),
                Country("JP", context.getString(R.string.country_jp)),
                Country("CN", context.getString(R.string.country_cn)),
                Country("DE", context.getString(R.string.country_de)),
                Country("FR", context.getString(R.string.country_fr)),
                Country("MX", context.getString(R.string.country_mx))
            )
        }

        _languages.update {
            listOf(
                Country("ko", context.getString(R.string.korean)),
                Country("en", context.getString(R.string.english))
            )
        }
    }

    fun updateCountry(countryCode: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val request = CountryRequest(countryCode)
            try {
                val response = apiService.updateCountry(request)
                val success = response.isSuccessful && response.body()?.boolean == true
                val body = response.body()
                Log.d("updateCountry", "response body: $body")

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

    fun getCountryName(code: String): String {
        return _countries.value.find { it.code == code }?.name ?: "없음"
    }

    fun getCountryLanguage(language: String): Country {
        return _languages.value.find { it.code == language } ?: Country("ko", "한국어")
    }
}
