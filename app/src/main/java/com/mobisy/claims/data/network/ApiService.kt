package com.mobisy.claims.data.network

import DynamicUiData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getFormData(
        @Url url: String
    ): Response<DynamicUiData>
}