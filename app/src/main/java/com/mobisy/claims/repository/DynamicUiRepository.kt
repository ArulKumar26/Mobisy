package com.mobisy.claims.repository

import DynamicUiData
import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.mobisy.claims.R
import com.mobisy.claims.data.model.CustomException
import com.mobisy.claims.data.network.ApiService
import com.mobisy.claims.data.network.BaseDataSource
import com.mobisy.claims.data.network.ResourceState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DynamicUiRepository @Inject constructor(
    //private val application: Application,
    @ApplicationContext private val context: Context,
    private val apiService: ApiService
) : BaseDataSource() {
    /*private val context by lazy {
        application.applicationContext
    }*/
    val apiResponse: MutableLiveData<ResourceState<DynamicUiData>> by lazy {
        MutableLiveData<ResourceState<DynamicUiData>>()
    }

    suspend fun getDynamicFormData(url: String) {
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getFormData(url)
                val apiData = getResult(response)
                apiResponse.postValue(apiData)
            } catch (error: Exception) {
                apiResponse.postValue(
                    ResourceState.error(
                        CustomException(
                            -1,
                            error.message
                                ?: context.getString(R.string.something_wrong)
                        )
                    )
                )
            }
        }
    }
}