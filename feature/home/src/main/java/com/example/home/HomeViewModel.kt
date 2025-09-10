package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ArticleDto
import com.example.domain.MainUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: MainUseCaseImpl) : ViewModel() {

    private val _articles = MutableStateFlow<List<ArticleDto>>(emptyList())
    val articles = _articles.asStateFlow()

    fun getTopHeadlines(country: String, category: String) = viewModelScope.launch {
        val response = useCase.getTopHeadlines(country, category)
        _articles.value = response
    }
}