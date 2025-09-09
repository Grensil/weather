package com.example.domain

import kotlinx.coroutines.flow.Flow

class MainUseCase(private val repository : MainRepository) {

    suspend fun getTopHeadlines(country: String, category: String) : List<ArticleDto> {
        return repository.getTopHeadlines(country,category)
    }
}