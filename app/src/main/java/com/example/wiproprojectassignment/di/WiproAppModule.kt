package com.example.wiproprojectassignment.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.wiproprojectassignment.remote.WiproAPICall
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * App module for retrofit
 */
@Module
class AppModule internal constructor(){
   private val BASE_URL = "https://dl.dropboxusercontent.com/"
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @Provides
    @Singleton
    fun getRemoteNetwork(retroFit: Retrofit): WiproAPICall {
        return retroFit.create(WiproAPICall::class.java)
    }

    @Provides
    @Singleton
    fun getAPiRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

}