package com.mobisy.claims.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobisy.claims.data.model.ResultClaimData
import com.mobisy.claims.data.network.Resource
import com.mobisy.claims.repository.ClaimRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val claimRepository: ClaimRepository
) : BaseViewModel() {
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
}