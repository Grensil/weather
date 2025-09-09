package com.example.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.MainRepository
import com.example.domain.MainUseCase
import com.example.domain.WeatherDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val weatherList = viewModel.weatherList.collectAsStateWithLifecycle(initialValue = emptyList())

    Scaffold(
        topBar = {
            Row(modifier = Modifier.statusBarsPadding().padding(horizontal = 16.dp)) {
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(Color.Gray, shape = RoundedCornerShape(16.dp))
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = item.region,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(
                            modifier = Modifier
                                .width(6.dp)
                                .fillMaxHeight()
                        )

                        Text(
                            text = item.status,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(
                            modifier = Modifier
                                .width(6.dp)
                                .fillMaxHeight()
                        )

                        Text(
                            text =  "(${item.temperature}\u00B0C)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
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

    override fun getWeatherList(): Flow<List<WeatherDto>> {
        return flowOf(fakeWeatherList)
    }
}