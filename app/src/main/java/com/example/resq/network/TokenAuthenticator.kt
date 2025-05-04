package com.example.resq.network

import com.example.resq.MainActivity.Companion.USER_TOKEN
import com.example.resq.network.RetrofitInstance.apiService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null

        USER_TOKEN = refreshAccessToken().toString()
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

    private fun refreshAccessToken(): String? {
        return try {
            apiService.getNewAccessToken(USER_TOKEN).body()?.accessToken
        } catch (e: Exception) {
            ""
        }
    }
}
