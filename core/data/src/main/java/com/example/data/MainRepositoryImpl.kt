package com.example.data

import com.example.domain.ArticleDto
import com.example.domain.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MainRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MainRepository {

    override suspend fun getTopHeadlines(country: String, category: String): List<ArticleDto> = withContext(Dispatchers.IO) {
        remoteDataSource.getHeadLineArticles(country = country, category = category)
    }

}