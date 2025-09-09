package com.example.domain

import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun getTopHeadlines(country: String, category: String) : List<ArticleDto>

}