package com.example.weatherapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.weatherapp.repositories.WeatherRepository
import com.example.weatherapp.servers.ApiClient
import com.example.weatherapp.servers.ApiService

class WeatherViewModel(val repository: WeatherRepository) : ViewModel() {
    constructor() : this(WeatherRepository(ApiClient().getClient().create(ApiService::class.java)))


    fun loadCurrentWeather(lat: Double, lon: Double, unit: String) =
        repository.getCurrentWeather(lat, lon, unit)
}