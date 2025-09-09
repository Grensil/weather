package com.example.domain

import kotlinx.coroutines.flow.Flow

class MainUseCase(private val repository : MainRepository) {

    fun getWeatherList() : Flow<List<WeatherDto>> {
        return repository.getWeatherList()
    }

}