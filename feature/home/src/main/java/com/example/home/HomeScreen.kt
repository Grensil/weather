package com.example.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val weatherList = viewModel.weatherList.collectAsState(initial = emptyList())

    Text(text = "${weatherList.value}")
}
