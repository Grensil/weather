package com.example.domain

import kotlinx.coroutines.flow.Flow

class MainUseCase(private val repository : MainRepository) {

    fun getWeatherList() : Flow<List<WeatherDto>> {
        return repository.getWeatherList()
    }

    fun getSubRegionWeatherList(region: String): List<WeatherDto>? {
        return repository.getSubRegionWeatherList(region)
    }
}