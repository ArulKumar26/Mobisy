package com.mobisy.claims.data.network

import com.mobisy.claims.data.model.CustomException
import retrofit2.Response

/**
 * Base Data source class with error handling
 */
abstract class BaseDataSource {
    protected fun <T> getResult(response: Response<T>): ResourceState<T> {
        try {
            var apiError: String? = null
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return ResourceState.success(body)
                }
            } else {
                val errorBody = response.errorBody()
                apiError = errorBody?.string()
            }
            return errorCheck(CustomException(response.code(), apiError!!))
        } catch (e: Exception) {
            return errorCheck(
                CustomException(
                    1000,
                    "Our server is under maintenance. We will resolve shortly!"
                )
            )
        }
    }

    private fun <T> errorCheck(customException: CustomException): ResourceState<T> {
        return ResourceState.error(customException)
    }
}

