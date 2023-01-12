package com.faris.newsapp.di.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.faris.newsapp.models.events.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(private val context: Context):Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        if (isConnectionAvailable()) {
            val originalRequest = chain.request()
            return chain.proceed(originalRequest)
        }else {
            throw NoInternetException()
        }
    }

    private fun isConnectionAvailable(): Boolean {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
        val network = connectivityManager.activeNetwork
        val connection = connectivityManager.getNetworkCapabilities(network)

        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                )
    }
}