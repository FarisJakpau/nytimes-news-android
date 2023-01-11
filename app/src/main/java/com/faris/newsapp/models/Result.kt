package com.faris.newsapp.models

sealed class Result <out T> {
    data class Success<out T>(val value:T): Result<T>()
    data class Failure(val error: Throwable): Result<Nothing>()
}