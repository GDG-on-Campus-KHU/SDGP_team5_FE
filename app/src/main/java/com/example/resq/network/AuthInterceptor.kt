package com.example.resq.network

import com.example.resq.MainActivity.Companion.USER_TOKEN
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val needAuthHeader = originalRequest.header("Need-Auth")
        val requestBuilder = originalRequest.newBuilder()

        if (needAuthHeader == "true") {
            requestBuilder.addHeader("Authorization", "Bearer $USER_TOKEN")
        }
        requestBuilder.removeHeader("Need-Auth")

        return chain.proceed(requestBuilder.build())
    }
}
