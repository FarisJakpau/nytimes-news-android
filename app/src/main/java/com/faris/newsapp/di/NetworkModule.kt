package com.faris.newsapp.di

import com.faris.newsapp.services.NetworkRequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    ): OkHttpClient {
        val connectionTimeOut: Long = 60
        val readTimeout: Long = 60

        val okHttpBuilder = OkHttpClient.Builder()
            .connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
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
    fun provideNetworkRequestManager() = NetworkRequestManager()
}