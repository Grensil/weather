package com.example.data

import android.util.Log
import com.example.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocalDataSource {

    private val favoriteArticles = mutableSetOf<Source>()
    private val _favoriteFlow = MutableStateFlow<Set<Source>>(emptySet())

    fun getFavoriteList() : Flow<Set<Source>> = _favoriteFlow.asStateFlow()

    fun addFavorite(source : Source) {
        favoriteArticles.add(source)
        _favoriteFlow.value = favoriteArticles.toSet()
    }

    fun removeFavorite(source : Source) {
        favoriteArticles.remove(source)
        _favoriteFlow.value = favoriteArticles.toSet()
    }

    fun isBookmarked(source: Source) : Boolean {
        return favoriteArticles.contains(source)
    }
}