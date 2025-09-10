package com.example.data

import com.example.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LocalDataSource {

    private val favoriteArticles = mutableSetOf<Source>()

    fun getFavoriteList() : Flow<Set<Source>> = flow {
        emit(favoriteArticles)
    }

    fun addFavorite(source : Source) {
        favoriteArticles.add(source)
    }

    fun removeFavorite(source : Source) {
        favoriteArticles.remove(source)
    }

    fun isBookmarked(source: Source) : Boolean {
        return favoriteArticles.contains(source)
    }

}