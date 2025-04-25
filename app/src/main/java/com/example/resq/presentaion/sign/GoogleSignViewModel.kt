@file:Suppress("DEPRECATION")

package com.example.resq.presentaion.sign

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.resq.MainActivity.Companion.USER_EMAIL
import com.example.resq.MainActivity.Companion.USER_ID
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GoogleSignViewModel : ViewModel() {

    private val _isAccount = MutableStateFlow(false)
    val isAccount: StateFlow<Boolean> = _isAccount

    fun saveUserInfo(context: Context, account: GoogleSignInAccount) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("userEmail", account.email)
        editor.putString("userId", account.id)
        editor.putString("userName", account.displayName)
        editor.apply()

        _isAccount.value = true
    }

    fun getUserInfo(context: Context): Boolean {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("userEmail", null)
        val userId = sharedPreferences.getString("userId", null)

        if (userEmail != null && userId != null) {
            USER_EMAIL = userEmail
            USER_ID = userId
            return true
        }
        return false
    }

    fun removeUserInfo(context: Context) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("userEmail")
        editor.remove("userId")
        editor.remove("userName")
        editor.remove("")
        editor.apply()

        Log.d("testt", "remove")
    }

    fun signOut(googleSignInClient: GoogleSignInClient) {
        googleSignInClient.signOut()
        Log.d("testt", "signout")
    }
    fun getUserName(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userName", null)
    }

    fun getUserEmail(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userEmail", null)
    }
}

