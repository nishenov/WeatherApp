package com.example.weatherapp.repositories

import com.example.weatherapp.servers.ApiService

class WeatherRepository(val api:ApiService) {
    fun getCurrentWeather(lat: Double, lon: Double, unit: String) =
        api.getCurrentWeather(lat, lon, unit, "22033c192a92c5abe0bca8ef99c6f661")
}