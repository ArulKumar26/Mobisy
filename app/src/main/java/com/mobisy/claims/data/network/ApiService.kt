package com.mobisy.claims.data.network

import JsonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getBreakingNews(
        @Url url: String
    ): Response<JsonResponse>
}