@file:Suppress("DEPRECATION")

package com.example.resq.presentaion.sign

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.MainActivity.Companion.EMERGENCY_CALL_NUMBER
import com.example.resq.MainActivity.Companion.USER_COUNTRY_CODE
import com.example.resq.MainActivity.Companion.USER_DISPLAY_NAME
import com.example.resq.MainActivity.Companion.USER_EMAIL
import com.example.resq.MainActivity.Companion.USER_TOKEN
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.network.model.AuthRequest
import com.example.resq.presentaion.resqbookmark.model.ResQBookmark
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GoogleSignViewModel : ViewModel() {

    private val _isToken = MutableStateFlow(false)
    val isToken: StateFlow<Boolean> = _isToken

    fun signIn(token: String, context: Context) {
        viewModelScope.launch {
            try {
                val authServerCode = AuthRequest(token)
                val response = apiService.googleSignIn(authServerCode).body()
                response?.let {
                    USER_TOKEN = it.accessToken
                    saveUserToken(context, it.accessToken)
                }
                _isToken.value = true
            } catch (e: Exception) {
                Log.d("signInTest", e.toString())
            }
        }
    }

    fun getUserToken(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userToken = sharedPreferences.getString("userToken", null) ?: ""

        return if (userToken.isNotEmpty()) {
            USER_TOKEN = userToken
            true
        } else {
            false
        }
    }

    fun saveUserToken(context: Context, token: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userToken", token)
        editor.apply()
    }

    fun removeUserToken(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("userToken")
        editor.apply()
    }

    fun signOut(googleSignInClient: GoogleSignInClient) {
        googleSignInClient.signOut()
    }

    fun getMyInfo() {
        viewModelScope.launch {
            try {
                val response = apiService.getMyInfo().body()
                response?.let {
                    USER_DISPLAY_NAME = it.userInfo.userName
                    USER_EMAIL = it.userInfo.userEmail
                    USER_COUNTRY_CODE = it.userInfo.userCountryCode
                    getEmerNumber(it.userInfo.userCountryCode)
                }
            } catch (e: Exception) {
                Log.d("getUserInfo", e.message.toString())
            }
        }
    }

    fun getEmerNumber(countryCode: String) {
        viewModelScope.launch {
            apiService.getCountryInfo(countryCode).body()
                ?.let { EMERGENCY_CALL_NUMBER = it.countryInfo.emerCall }
        }
    }

    fun getFavoriteResQList(onResult: (List<ResQBookmark>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = apiService.getFavoriteResQList().body()
                val favoriteResQList = response?.favoriteResQList ?: emptyList()
                onResult(favoriteResQList)
            } catch (e: Exception) {
                Log.d("getFavoriteResQList", e.message.toString())
            }
        }
    }
}
