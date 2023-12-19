package com.konkuk.sadelivery.presentation.base

import android.util.Log
import com.konkuk.sadelivery.utils.ApiState
import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T> makeRequest(call: suspend () -> Response<T>): ApiState<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                Log.d("Network", "API Succeed Response: $response")
                ApiState.Success(response.body())
            } else {
                ApiState.Error(response.errorBody())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ApiState.Error(null)
        }
    }

}