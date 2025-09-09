package com.example.domain


data class WeatherDto(
    var region : String,
    var status : String,
    var temperature : Float,
)