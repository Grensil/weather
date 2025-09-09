package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.MainUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

class HomeViewModel(private val useCase: MainUseCase) : ViewModel() {

    val weatherList = useCase.getWeatherList().shareIn(
        scope = viewModelScope,
        started = SharingStarted.Companion.Eagerly,
        replay = 1
    )
}