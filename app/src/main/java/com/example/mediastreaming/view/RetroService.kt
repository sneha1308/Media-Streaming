package com.example.mediastreaming.view

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RetroService {

    @GET
    fun getMediaUrls(@Url url:String): Call<MediaStream>

}