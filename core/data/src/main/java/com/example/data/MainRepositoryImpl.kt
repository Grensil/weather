package com.example.data

import com.example.domain.MainRepository
import com.example.domain.WeatherDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class MainRepositoryImpl : MainRepository {

    private val fakeWeatherList = listOf(
        WeatherEntity(region = "서울", status = "맑음",temperature=  20.8f),
        WeatherEntity(region = "부산", status = "흐림",temperature =  18.0f),
        WeatherEntity(region = "대구", status = "비", temperature = 15.1f),
        WeatherEntity(region = "인천", status = "맑음", 19.7f),
        WeatherEntity(region = "광주", status = "흐림", 17.3f),
        WeatherEntity(region = "대전", status = "맑음", 21.3f),
        WeatherEntity(region = "울산", status = "비", temperature = 16.7f)
    )

    override fun getWeatherList(): Flow<List<WeatherDto>> {
        return flowOf(fakeWeatherList.map { it.toWeatherDto() })
    }
}