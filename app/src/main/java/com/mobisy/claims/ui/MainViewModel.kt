package com.mobisy.claims.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobisy.claims.R
import com.mobisy.claims.data.model.ResultClaimData
import com.mobisy.claims.data.network.Resource
import com.mobisy.claims.extensions.showMessage
import com.mobisy.claims.repository.ClaimRepository
import com.mobisy.claims.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val application: Application,
    private val claimRepository: ClaimRepository
) : BaseViewModel() {
    private val context by lazy {
        application.applicationContext
    }

    val dynamicUiData by lazy { claimRepository.apiResponse }
    private val claims = MutableLiveData<Resource<List<ResultClaimData>>>()

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        claims.postValue(Resource.error("Something Went Wrong", null))
    }

    fun fetchClaims() {
        viewModelScope.launch(exceptionHandler) {
            claims.postValue(Resource.loading(null))
            val claimsList = claimRepository.getClaims()
            claims.postValue(Resource.success(claimsList))
        }
    }

    fun insertClaims(resultClaimData: ResultClaimData) {
        viewModelScope.launch(exceptionHandler) {
            claimRepository.saveClaims(resultClaimData)
        }
    }

    fun getClaims(): LiveData<Resource<List<ResultClaimData>>> = claims

    fun getJsonData() {
        viewModelScope.launch {
            try {
                if (NetworkUtils.isNetWorkAvailable(context)) {
                    claims.postValue(Resource.loading(null))
                    claimRepository.getDynamicFormData("https://demo.ezetap.com/mobileapps/android_assignment.json")
                } else {
                    context.showMessage(context.getString(R.string.no_internet))
                }
            } catch (error: Exception) {
                print(error)
            }
        }
    }
}