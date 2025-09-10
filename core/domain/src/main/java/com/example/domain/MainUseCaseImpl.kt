package com.example.domain

import kotlinx.coroutines.flow.Flow

interface MainUseCase {
    suspend operator fun invoke(country: String, category: String): Flow<List<ArticleDto>>
    suspend fun getFavoriteList() : Flow<Set<SourceDto>>
    fun addFavorite(source : SourceDto)
    fun removeFavorite(source : SourceDto)
    fun isBookmarked(source: SourceDto) : Boolean
}

class MainUseCaseImpl(private val repository : MainRepository) : MainUseCase {

    override suspend operator fun invoke(country: String, category: String) : Flow<List<ArticleDto>> {
        return repository.getTopHeadlines(country, category)
    }

    override suspend fun getFavoriteList(): Flow<Set<SourceDto>> {
        return repository.getFavoriteList()
    }

    override fun addFavorite(source: SourceDto) {
        repository.addFavorite(source)
    }

    override fun removeFavorite(source: SourceDto) {
        repository.removeFavorite(source)
    }

    override fun isBookmarked(source: SourceDto): Boolean {
        return repository.isBookmarked(source)
    }

}