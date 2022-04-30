package com.mobisy.claims.repository

import DynamicUiData
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mobisy.claims.data.db.ClaimDao
import com.mobisy.claims.data.model.ResultClaimData
import com.mobisy.claims.data.network.ApiService
import com.mobisy.claims.data.network.BaseDataSource
import com.mobisy.claims.data.network.ResourceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClaimRepository @Inject constructor(
    private val apiService: ApiService,
    private val claimDao: ClaimDao
) : BaseDataSource() {

    val apiResponse: MutableLiveData<ResourceState<DynamicUiData>> by lazy {
        MutableLiveData<ResourceState<DynamicUiData>>()
    }

    suspend fun saveClaims(resultClaimData: ResultClaimData) {
        withContext(Dispatchers.IO) {
            claimDao.insertData(resultClaimData)
        }
    }

    suspend fun getDynamicFormData(url: String) {
        withContext(Dispatchers.IO) {
            val response = apiService.getFormData(url)
            val apiData = getResult(response)
            Log.d("ApiData ->", apiData.toString())
            //apiResponse.postValue(apiData)
        }
    }

    suspend fun getClaims(): List<ResultClaimData> = claimDao.getAll()

}

