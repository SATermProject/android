package com.konkuk.sadelivery.utils

import android.util.Log
import okhttp3.ResponseBody


sealed class ApiState<T>(
) {
    class Success<T>(val data: T?) : ApiState<T>() {
        var hasBeenHandled = false
            private set

        fun getContentIfNotHandled(): T? {
            return if (hasBeenHandled) {
                null
            } else {
                hasBeenHandled = true
                data
            }
        }
    }

    class Error<T>(val err: ResponseBody?) : ApiState<T>()
    class Loading<T> : ApiState<T>()

    fun <R> byState(
        onSuccess: (T) -> (R?),
        onFailure: (ResponseBody?) -> (Unit) = {},
        onLoading: () -> (Unit) = {}
    ): R? {
        when (this) {
            is Success -> {
                return getContentIfNotHandled()?.let(onSuccess)
            }
            is Error -> {
                Log.e("Network", "Network ERROR: ${err?.string()}")
                onFailure(err)
            }
            is Loading -> {
                onLoading.invoke()
            }
        }
        return null
    }

}
