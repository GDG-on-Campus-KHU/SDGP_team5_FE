@file:Suppress("DEPRECATION")

package com.example.resq.presentaion.sign

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.resq.MainActivity.Companion.USER_DISPLAY_NAME
import com.example.resq.MainActivity.Companion.USER_EMAIL
import com.example.resq.MainActivity.Companion.USER_TOKEN
import com.example.resq.MainActivity.Companion.googleSignInClient
import com.example.resq.network.RetrofitInstance.apiService
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GoogleSignViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun signIn(token: String, context: Context) {
        viewModelScope.launch {
            try {
                Log.d("signInTest", token)
//                val response = apiService.googleSignIn(token)
//                Log.d("signInTest", response.body()?.accessToken ?: "없다")
//                response.body()?.let { USER_TOKEN = it.accessToken }
//                removeUserToken(context)
//                saveUserToken(context, USER_TOKEN)
            } catch (e: Exception) {
                Log.d("signInTest", e.toString())
            }
            _isLoading.value = false
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

    private fun saveUserToken(context: Context, token: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userToken", token)
        editor.apply()
    }

    private fun removeUserToken(context: Context) {
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
                apiService.getMyInfo().body()?.let {
                    USER_DISPLAY_NAME = it.userInfo.userName
                    USER_EMAIL = it.userInfo.userEmail
                }
            } catch (e: Exception) {
                Log.d("getUserInfo", e.message.toString())
            }
        }
    }
}
