package com.example.data

import android.util.Log
import com.example.data.model.Source
import com.example.domain.ArticleDto
import com.example.domain.MainRepository
import com.example.domain.SourceDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class MainRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : MainRepository {

    override suspend fun getTopHeadlines(country: String, category: String): Flow<List<ArticleDto>> {
        return withContext(Dispatchers.IO) {
            val local = localDataSource.getFavoriteList()
            val remote = remoteDataSource.getHeadLineArticles(country = country, category = category)
            combine(local, remote) { localList, remoteList ->
                remoteList.articles.map { article ->
                    val articleDto = article.toArticleDto()
                    val isBookmarked = localList.any { favoriteSource ->
                        favoriteSource.name == article.source.name &&
                        favoriteSource.id == article.source.id
                    }
                    articleDto.copy(bookmarked = isBookmarked)
                }
            }
        }
    }

    override suspend fun getFavoriteList(): Flow<Set<SourceDto>> {
        return localDataSource.getFavoriteList().map { sourceSet ->
            sourceSet.map { source ->
                SourceDto(id = source.id, name = source.name)
            }.toSet()
        }
    }

    override fun addFavorite(source: SourceDto) {
        localDataSource.addFavorite(source.toSourceEntity())
    }

    override fun removeFavorite(source: SourceDto) {
        localDataSource.removeFavorite(source.toSourceEntity())
    }

    override fun isBookmarked(source: SourceDto): Boolean {
        return localDataSource.isBookmarked(source.toSourceEntity())
    }
}