package com.mobisy.claims.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.mobisy.claims.R
import com.mobisy.claims.data.model.CustomException
import com.mobisy.claims.data.network.ResourceState
import com.mobisy.claims.extensions.showMessage
import com.mobisy.claims.repository.DynamicUiRepository
import com.mobisy.claims.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicFormViewModel @Inject constructor(
    private val application: Application,
    private val dynamicUiRepository: DynamicUiRepository
) : BaseViewModel() {
    private val context by lazy {
        application.applicationContext
    }
    private val dynamicUiData by lazy { dynamicUiRepository.apiResponse }

    init {
        getDynamicFormData()
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        dynamicUiData.postValue(
            ResourceState.error(
                CustomException(
                    -1,
                    exception.message
                        ?: context.getString(R.string.something_wrong)
                )
            )
        )
    }

    private fun getDynamicFormData() {
        viewModelScope.launch(exceptionHandler) {
            try {
                if (NetworkUtils.isNetWorkAvailable(context)) {
                    dynamicUiData.postValue(ResourceState.loading())
                    dynamicUiRepository.getDynamicFormData("https://demo.ezetap.com/mobileapps/android_assignment.json")
                } else {
                    context.showMessage(context.getString(R.string.no_internet))
                }
            } catch (error: Exception) {
                dynamicUiData.postValue(
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