package com.example.domain

interface MainUseCase {
    suspend fun getTopHeadlines(country: String, category: String): List<ArticleDto>
}

class MainUseCaseImpl(private val repository : MainRepository) : MainUseCase {

    override suspend fun getTopHeadlines(country: String, category: String) : List<ArticleDto> {
        return repository.getTopHeadlines(country,category)
    }
}