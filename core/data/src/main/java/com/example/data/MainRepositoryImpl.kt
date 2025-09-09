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

    private val fakeSubWeatherList = mapOf(
        "서울" to listOf(
            WeatherEntity(region = "강남구", status = "맑음", temperature = 19.2f),
            WeatherEntity(region = "강동구", status = "구름많음", temperature = 18.8f),
            WeatherEntity(region = "강서구", status = "맑음", temperature = 18.1f),
            WeatherEntity(region = "영등포구", status = "흐림", temperature = 18.9f),
            WeatherEntity(region = "마포구", status = "맑음", temperature = 19.0f),
            WeatherEntity(region = "종로구", status = "구름많음", temperature = 18.7f)
        ),
        "부산" to listOf(
            WeatherEntity(region = "해운대구", status = "비", temperature = 19.8f),
            WeatherEntity(region = "남구", status = "흐림", temperature = 20.5f),
            WeatherEntity(region = "사하구", status = "구름많음", temperature = 20.1f),
            WeatherEntity(region = "기장군", status = "맑음", temperature = 19.9f),
            WeatherEntity(region = "부산진구", status = "흐림", temperature = 20.3f)
        ),
        "인천" to listOf(
            WeatherEntity(region = "연수구", status = "비", temperature = 17.5f),
            WeatherEntity(region = "남동구", status = "흐림", temperature = 18.1f),
            WeatherEntity(region = "서구", status = "비", temperature = 17.3f),
            WeatherEntity(region = "미추홀구", status = "구름많음", temperature = 17.9f)
        ),
        "대구" to listOf(
            WeatherEntity(region = "수성구", status = "맑음", temperature = 19.5f),
            WeatherEntity(region = "달서구", status = "구름많음", temperature = 18.8f),
            WeatherEntity(region = "북구", status = "맑음", temperature = 19.2f),
            WeatherEntity(region = "중구", status = "흐림", temperature = 19.0f)
        ),
        "대전" to listOf(
            WeatherEntity(region = "유성구", status = "구름많음", temperature = 17.2f),
            WeatherEntity(region = "서구", status = "비", temperature = 16.8f),
            WeatherEntity(region = "중구", status = "맑음", temperature = 17.1f),
            WeatherEntity(region = "동구", status = "흐림", temperature = 16.5f)
        ),
        "광주" to listOf(
            WeatherEntity(region = "북구", status = "맑음", temperature = 21.8f),
            WeatherEntity(region = "서구", status = "구름많음", temperature = 21.1f),
            WeatherEntity(region = "남구", status = "맑음", temperature = 21.5f),
            WeatherEntity(region = "광산구", status = "흐림", temperature = 21.0f)
        ),
        "울산" to listOf(
            WeatherEntity(region = "남구", status = "흐림", temperature = 19.9f),
            WeatherEntity(region = "북구", status = "비", temperature = 19.4f),
            WeatherEntity(region = "중구", status = "구름많음", temperature = 20.0f),
            WeatherEntity(region = "울주군", status = "맑음", temperature = 19.2f)
        ),
        "수원" to listOf(
            WeatherEntity(region = "영통구", status = "비", temperature = 17.5f),
            WeatherEntity(region = "팔달구", status = "흐림", temperature = 17.0f),
            WeatherEntity(region = "장안구", status = "구름많음", temperature = 17.3f),
            WeatherEntity(region = "권선구", status = "비", temperature = 16.8f)
        ),
        "창원" to listOf(
            WeatherEntity(region = "성산구", status = "맑음", temperature = 21.1f),
            WeatherEntity(region = "의창구", status = "구름많음", temperature = 20.6f),
            WeatherEntity(region = "마산합포구", status = "흐림", temperature = 20.9f),
            WeatherEntity(region = "진해구", status = "맑음", temperature = 20.7f)
        ),
        "고양" to listOf(
            WeatherEntity(region = "일산동구", status = "구름많음", temperature = 18.4f),
            WeatherEntity(region = "일산서구", status = "흐림", temperature = 17.9f),
            WeatherEntity(region = "덕양구", status = "맑음", temperature = 18.2f)
        ),
        "용인" to listOf(
            WeatherEntity(region = "수지구", status = "맑음", temperature = 17.8f),
            WeatherEntity(region = "기흥구", status = "구름많음", temperature = 17.4f),
            WeatherEntity(region = "처인구", status = "흐림", temperature = 17.9f)
        ),
        "성남" to listOf(
            WeatherEntity(region = "분당구", status = "흐림", temperature = 19.2f),
            WeatherEntity(region = "수정구", status = "비", temperature = 18.7f),
            WeatherEntity(region = "중원구", status = "구름많음", temperature = 18.9f)
        ),
        "청주" to listOf(
            WeatherEntity(region = "서원구", status = "비", temperature = 16.7f),
            WeatherEntity(region = "흥덕구", status = "흐림", temperature = 16.2f),
            WeatherEntity(region = "상당구", status = "구름많음", temperature = 16.5f),
            WeatherEntity(region = "청원구", status = "비", temperature = 16.1f)
        ),
        "천안" to listOf(
            WeatherEntity(region = "동남구", status = "맑음", temperature = 19.8f),
            WeatherEntity(region = "서북구", status = "구름많음", temperature = 19.2f)
        ),
        "전주" to listOf(
            WeatherEntity(region = "완산구", status = "구름많음", temperature = 20.4f),
            WeatherEntity(region = "덕진구", status = "흐림", temperature = 19.8f)
        ),
        "포항" to listOf(
            WeatherEntity(region = "남구", status = "흐림", temperature = 21.5f),
            WeatherEntity(region = "북구", status = "비", temperature = 20.9f)
        ),
        "제주" to listOf(
            WeatherEntity(region = "제주시", status = "맑음", temperature = 23.1f),
            WeatherEntity(region = "서귀포시", status = "구름많음", temperature = 22.3f)
        ),
        "춘천" to listOf(
            WeatherEntity(region = "중앙동", status = "비", temperature = 15.6f),
            WeatherEntity(region = "퇴계동", status = "흐림", temperature = 15.0f),
            WeatherEntity(region = "소양동", status = "구름많음", temperature = 15.4f)
        ),
        "강릉" to listOf(
            WeatherEntity(region = "교동", status = "구름많음", temperature = 20.1f),
            WeatherEntity(region = "포남동", status = "맑음", temperature = 19.5f),
            WeatherEntity(region = "홍제동", status = "흐림", temperature = 19.9f)
        ),
        "원주" to listOf(
            WeatherEntity(region = "중앙동", status = "맑음", temperature = 17.7f),
            WeatherEntity(region = "명륜동", status = "구름많음", temperature = 17.2f),
            WeatherEntity(region = "단계동", status = "흐림", temperature = 17.5f)
        )
    )

    override fun getWeatherList(): Flow<List<WeatherDto>> {
        return flowOf(fakeWeatherList.map { it.toWeatherDto() })
    }

    override fun getSubRegionWeatherList(region: String): List<WeatherDto>? {
        return fakeSubWeatherList[region]?.map { it.toWeatherDto() }
    }
}