package com.example.mediastreaming.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {

    fun getInstance(): Retrofit {

        val gson: Gson = GsonBuilder().setLenient().create()
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://66anmbvqtuyabd7dpvy5vwqp2e0drbfz.lambda-url.ap-southeast-1.on.aws")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(mOkHttpClient)
            .build()

    }

}