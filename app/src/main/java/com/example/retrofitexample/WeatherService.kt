package com.example.retrofitexample

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("data/2.5/weather?")
    fun buscarTempoAtual(@Query("lat")lat:String, @Query("lon") lon:String, @Query("APPID") app_id:String):Call<WeatherResponse>
}