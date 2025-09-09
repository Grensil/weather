package com.example.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.MainRepository
import com.example.domain.MainUseCase
import com.example.domain.WeatherDto
import com.example.home.component.WeatherItemView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val weatherList = viewModel.weatherList.collectAsStateWithLifecycle(initialValue = emptyList())
    val expandedItems = rememberSaveable { mutableStateOf<Set<String>>(emptySet()) }

    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp)) {
                Text(
                    text = "Today's Weather",
                    fontSize = 32.sp,
                    modifier = Modifier.padding(vertical = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(weatherList.value.size) { index ->
                    val item = weatherList.value[index]

                    WeatherItemView(
                        item = item,
                        subItems = viewModel.getSubWeatherList(item.region),
                        expanded = expandedItems.value.contains(item.region),
                        expandOnClick = {
                            if (expandedItems.value.contains(item.region)) {
                                expandedItems.value = expandedItems.value - item.region
                            } else {
                                expandedItems.value = expandedItems.value + item.region
                            }
                        })
                }
            }
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    val fakeRepository = FakeRepository()
    val fakeUseCase = MainUseCase(fakeRepository)
    val fakeViewModel = HomeViewModel(fakeUseCase)
    HomeScreen(fakeViewModel)
}

class FakeRepository : MainRepository {

    private val fakeWeatherList = listOf(
        WeatherDto(region = "서울", status = "맑음", temperature = 20.8f),
        WeatherDto(region = "부산", status = "흐림", temperature = 18.0f),
        WeatherDto(region = "대구", status = "비", temperature = 15.1f),
        WeatherDto(region = "인천", status = "맑음", 19.7f),
        WeatherDto(region = "광주", status = "흐림", 17.3f),
        WeatherDto(region = "대전", status = "맑음", 21.3f),
        WeatherDto(region = "울산", status = "비", temperature = 16.7f)
    )

    private val fakeSubWeatherList = mapOf(
        "서울" to listOf(
            WeatherDto(region = "강남구", status = "맑음", temperature = 19.2f),
            WeatherDto(region = "강동구", status = "구름많음", temperature = 18.8f),
            WeatherDto(region = "강서구", status = "맑음", temperature = 18.1f),
            WeatherDto(region = "영등포구", status = "흐림", temperature = 18.9f),
            WeatherDto(region = "마포구", status = "맑음", temperature = 19.0f),
            WeatherDto(region = "종로구", status = "구름많음", temperature = 18.7f)
        )
    )

    override fun getWeatherList(): Flow<List<WeatherDto>> {
        return flowOf(fakeWeatherList)
    }

    override fun getSubRegionWeatherList(region: String): List<WeatherDto>? {
        return fakeSubWeatherList[region]
    }
}