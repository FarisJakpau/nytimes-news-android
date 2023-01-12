package com.faris.newsapp.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

inline fun <T, R> T?.guard(block: () -> R): T {
    if (this == null) {
        block()
        throw IllegalArgumentException("guard block must return from enclosing function")
    }

    return this
}

fun Context?.isPermissionGranted(permission: String): Boolean {
    return this != null && ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}