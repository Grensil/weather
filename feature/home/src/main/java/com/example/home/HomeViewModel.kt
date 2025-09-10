package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ArticleDto
import com.example.domain.MainUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: MainUseCase) : ViewModel() {

    private val _articles = MutableStateFlow<List<ArticleDto>>(emptyList())
    val articles = _articles.asStateFlow()

    init {
        getTopHeadlines("us", "business")
    }

    fun getTopHeadlines(country: String, category: String) = viewModelScope.launch {
        try {
            useCase(country, category)
                .catch { exception ->
                    _articles.value = emptyList()
                }
                .collect { articleList ->
                    _articles.value = articleList
                }
        } catch (_: Exception) {
            _articles.value = emptyList()
        }
    }
}