package com.example.resq.network

import android.content.Context
import com.example.resq.MainActivity.Companion.USER_TOKEN
import com.example.resq.network.RetrofitInstance.apiService
import com.example.resq.network.model.NewTokenRequest
import com.example.resq.presentaion.sign.GoogleSignViewModel
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(private val context: Context) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null

        runBlocking {
            try {
                val token = NewTokenRequest(USER_TOKEN)
                val response = apiService.getNewAccessToken(token)
                response.body()?.let { USER_TOKEN = it.accessToken }
                GoogleSignViewModel().saveUserToken(context, USER_TOKEN)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return if (USER_TOKEN.isNotEmpty()) {
            response.request.newBuilder()
                .header("Authorization", "Bearer $USER_TOKEN")
                .build()
        } else {
            null
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}
