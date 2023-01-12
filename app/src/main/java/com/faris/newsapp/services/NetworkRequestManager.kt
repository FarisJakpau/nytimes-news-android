package com.faris.newsapp.services

import android.util.Log
import com.faris.newsapp.models.AppError
import retrofit2.Response
import com.faris.newsapp.models.Result
import com.faris.newsapp.models.events.NoInternetException
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
//                Log.e("Error Main", response.code().toString())
                handleFailureInResponse(response)
            }
        } catch (exception: Exception) {
//            Log.e("Error", exception.message.toString())
            handleFailureInRequest(exception)
        }
    }

    inline fun <reified T> handleFailureInRequest(throwable: Throwable): Result<T> {
        return when(throwable) {
            is NoInternetException -> {
                Result.Failure(AppError(AppError.Code.Network, throwable))
            }
            else -> {
                Result.Failure(AppError(AppError.Code.InvalidData, throwable))
            }
        }
    }

    inline fun <reified T> handleFailureInResponse(response: Response<T>): Result<T> {
        return Result.Failure(getAppError(response))
    }

    fun <T> getAppError(response: Response<T>): AppError {

        return when(response.code()) {
            500 -> {
                AppError(AppError.Code.ServerError)
            }
            else -> {
                AppError(AppError.Code.BadRequest)
            }
        }
    }
}