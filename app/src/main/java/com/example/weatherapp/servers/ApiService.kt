package com.example.weatherapp.servers

import com.example.weatherapp.data.models.CurrentResponseApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units : String,
        @Query("appid") apiKey : String
    ) : Call<CurrentResponseApi>
}