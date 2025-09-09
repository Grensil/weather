package com.example.data

import com.example.domain.MainRepository
import com.example.domain.WeatherDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class MainRepositoryImpl : MainRepository {

    private val fakeWeatherList = listOf(
        WeatherEntity(region = "서울", status = "맑음", temperature = 18.5f),
        WeatherEntity(region = "부산", status = "흐림", temperature = 20.2f),
        WeatherEntity(region = "인천", status = "비", temperature = 17.8f),
        WeatherEntity(region = "대구", status = "맑음", temperature = 19.1f),
        WeatherEntity(region = "대전", status = "구름많음", temperature = 16.9f),
        WeatherEntity(region = "광주", status = "맑음", temperature = 21.3f),
        WeatherEntity(region = "울산", status = "흐림", temperature = 19.7f),
        WeatherEntity(region = "수원", status = "비", temperature = 17.2f),
        WeatherEntity(region = "창원", status = "맑음", temperature = 20.8f),
        WeatherEntity(region = "고양", status = "구름많음", temperature = 18.1f),
        WeatherEntity(region = "용인", status = "맑음", temperature = 17.6f),
        WeatherEntity(region = "성남", status = "흐림", temperature = 18.9f),
        WeatherEntity(region = "청주", status = "비", temperature = 16.4f),
        WeatherEntity(region = "천안", status = "맑음", temperature = 19.5f),
        WeatherEntity(region = "전주", status = "구름많음", temperature = 20.1f),
        WeatherEntity(region = "포항", status = "흐림", temperature = 21.2f),
        WeatherEntity(region = "제주", status = "맑음", temperature = 22.7f),
        WeatherEntity(region = "춘천", status = "비", temperature = 15.3f),
        WeatherEntity(region = "강릉", status = "구름많음", temperature = 19.8f),
        WeatherEntity(region = "원주", status = "맑음", temperature = 17.4f)
    )

    override fun getWeatherList(): Flow<List<WeatherDto>> {
        return flowOf(fakeWeatherList.map { it.toWeatherDto() })
    }
}