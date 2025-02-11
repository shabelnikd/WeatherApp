package com.shabelnikd.weatherapp.utils

import android.util.Log

suspend fun <T> safeApiCall(
    call: suspend () -> T,
    errorMessage: String
): Result<T> {
    try {
        val result = call()
        return Result.success(result)
    } catch (e: Exception) {
        Log.e("ALLD", errorMessage, e)
        return Result.failure(e)
    }
}