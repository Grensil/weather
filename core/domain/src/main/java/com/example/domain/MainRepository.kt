package com.example.domain

import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getWeatherList() : Flow<List<WeatherDto>>

}