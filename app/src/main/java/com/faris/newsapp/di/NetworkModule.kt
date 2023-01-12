package com.faris.newsapp.di

import android.content.Context
import com.faris.newsapp.di.interceptors.NetworkInterceptor
import com.faris.newsapp.services.NetworkRequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkInterceptor: NetworkInterceptor
    ): OkHttpClient {
        val connectionTimeOut: Long = 60
        val readTimeout: Long = 60

        val okHttpBuilder = OkHttpClient.Builder()
            .connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
            .addInterceptor(networkInterceptor)
            .readTimeout(readTimeout, TimeUnit.SECONDS)

        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkInterceptor(
        @ApplicationContext context: Context
    ): NetworkInterceptor {
        return NetworkInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideNetworkRequestManager() = NetworkRequestManager()
}