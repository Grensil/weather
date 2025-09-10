package com.example.domain

import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    suspend operator fun invoke(country: String, category: String): Flow<List<ArticleDto>>
}

class MainUseCaseImpl(private val repository : MainRepository) : MainUseCase {

    override suspend operator fun invoke(country: String, category: String) : Flow<List<ArticleDto>> {
        return repository.getTopHeadlines(country, category)
    }
}