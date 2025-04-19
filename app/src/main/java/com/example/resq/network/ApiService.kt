package com.example.resq.network

import com.example.resq.presentaion.resqdetail.model.ResQDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("situation/actions/case")
    suspend fun getResQDetail(
        @Query("slug") slug: String,
        @Query("language") language: String
    ): Response<ResQDetailResponse>
}