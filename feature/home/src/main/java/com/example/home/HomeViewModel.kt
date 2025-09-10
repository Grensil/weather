package com.example.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ArticleDto
import com.example.domain.MainUseCase
import com.example.domain.SourceDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
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
                .distinctUntilChanged()
                .collect { articleList ->
                    Log.d("Logd","articleList ${articleList}")
                    _articles.value = articleList
                }
        } catch (_: Exception) {
            _articles.value = emptyList()
        }
    }

    fun addFavorite(sourceDto: SourceDto) {
        useCase.addFavorite(sourceDto)
    }

    fun removeFavorite(sourceDto: SourceDto) {
        useCase.removeFavorite(sourceDto)
    }
}