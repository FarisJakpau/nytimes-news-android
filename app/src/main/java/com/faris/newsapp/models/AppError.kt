package com.faris.newsapp.models

import java.lang.RuntimeException

class AppError(
    val code: Code,
    val underlyingError: Throwable? = null
): RuntimeException() {
    enum class Code
    {
        InvalidData,
        Network,
        BadRequest,

        // HTTP server error with code 500.
        ServerError
    }
}