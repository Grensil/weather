package com.example.data

import com.example.domain.WeatherDto

fun WeatherEntity.toWeatherDto() : WeatherDto {
    return WeatherDto(region = this.region,
        status = this.status,
        temperature = this.temperature)
}
