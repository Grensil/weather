package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.MainUseCase
import com.example.domain.WeatherDto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

class HomeViewModel(private val useCase: MainUseCase) : ViewModel() {

    val weatherList = useCase.getWeatherList().shareIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.Eagerly,
        replay = 1
    )


    fun getSubWeatherList(region : String) : List<WeatherDto>? {
        return useCase.getSubRegionWeatherList(region = region)
    }
}