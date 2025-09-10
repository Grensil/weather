package com.example.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface MainRepository {
    suspend fun getTopHeadlines(country: String, category: String) : Flow<List<ArticleDto>>

    suspend fun getFavoriteList() : Flow<Set<SourceDto>>
    fun addFavorite(source : SourceDto)
    fun removeFavorite(source : SourceDto)
    fun isBookmarked(source: SourceDto) : Boolean
}