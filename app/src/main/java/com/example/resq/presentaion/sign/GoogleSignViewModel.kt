@file:Suppress("DEPRECATION")

package com.example.resq.presentaion.sign

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.MainActivity.Companion.FAVORITE_RESQ_LIST
import com.example.resq.MainActivity.Companion.USER_DISPLAY_NAME
import com.example.resq.MainActivity.Companion.USER_EMAIL
import com.example.resq.MainActivity.Companion.USER_TOKEN
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.network.model.AuthRequest
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
                }
            } catch (e: Exception) {
                Log.d("getUserInfo", e.message.toString())
            }
        }
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
                FAVORITE_RESQ_LIST = response?.favoriteResQList ?: emptyList()
            } catch (e: Exception) {
                Log.d("getFavoriteResQList", e.message.toString())
            }
        }
    }
}
