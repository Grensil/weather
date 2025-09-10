package com.example.data

import com.example.domain.ArticleDto
import com.example.domain.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext


class MainRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSourceImpl: RemoteDataSourceImpl
) : MainRepository {

    override suspend fun getTopHeadlines(country: String, category: String): Flow<List<ArticleDto>> {
        return withContext(Dispatchers.IO) {
            val local = localDataSource.getFavoriteList()
            val remote = remoteDataSourceImpl.getHeadLineArticles(country = country, category = category)

            combine(local, remote) { localList, remoteList ->
                val result = remoteList.articles.map { article ->
                    val articleDto = article.toArticleDto()
                    val isBookmarked = localList.any { favoriteSource ->
                        favoriteSource.name == article.source.name && 
                        favoriteSource.id == article.source.id
                    }
                    articleDto.copy(bookmarked = isBookmarked)
                }
                result
            }
        }
    }
}