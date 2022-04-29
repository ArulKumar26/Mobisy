package com.mobisy.claims.repository

import android.util.Log
import com.mobisy.claims.data.db.ClaimDao
import com.mobisy.claims.data.model.ResultClaimData
import com.mobisy.claims.data.network.ApiService
import com.mobisy.claims.data.network.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClaimRepository @Inject constructor(
    private val apiService: ApiService,
    private val claimDao: ClaimDao
) : BaseDataSource() {

    suspend fun saveClaims(resultClaimData: ResultClaimData) {
        withContext(Dispatchers.IO) {
            claimDao.insertData(resultClaimData)
        }
    }

    suspend fun getJsonData(url: String) {
        withContext(Dispatchers.IO) {
            val response = apiService.getBreakingNews(url)
            val apiData = getResult(response)

            Log.d("ApiData ->", apiData.toString())
        }
    }

    suspend fun getClaims(): List<ResultClaimData> = claimDao.getAll()

}

