package com.faris.newsapp.services

import com.faris.newsapp.models.AppError
import retrofit2.Response
import com.faris.newsapp.models.Result
import com.faris.newsapp.utils.guard
import com.google.gson.JsonParseException

class NetworkRequestManager {
    suspend inline fun <reified T> apiRequest(crossinline apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall.invoke()

            return if (response.isSuccessful) {
                val body = response.body().guard {
                    Result.Failure(AppError(AppError.Code.InvalidData))
                }
                Result.Success(body)
            } else {
                handleFailureInResponse(response)
            }
        } catch (exception: Exception) {
            handleFailureInRequest(exception)
        }
    }

    inline fun <reified T> handleFailureInRequest(throwable: Throwable): Result<T> {
        return Result.Failure(AppError(AppError.Code.InvalidData, throwable))
    }

    inline fun <reified T> handleFailureInResponse(response: Response<T>): Result<T> {
        return Result.Failure(getAppError(response))
    }

    fun <T> getAppError(response: Response<T>): AppError {
        return  AppError(AppError.Code.ServerError)
    }
}