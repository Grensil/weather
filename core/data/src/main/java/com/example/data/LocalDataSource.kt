package com.example.data

import com.example.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocalDataSource {

    private val _favoriteFlow = MutableStateFlow<Map<Source, Boolean>>(emptyMap())

    fun getFavoriteList(): Flow<Map<Source, Boolean>> = _favoriteFlow.asStateFlow()

    fun addFavorite(source: Source) {
        _favoriteFlow.value = _favoriteFlow.value + (source to true)
    }

    fun removeFavorite(source: Source) {
        _favoriteFlow.value = _favoriteFlow.value + (source to false)
    }

    fun isBookmarked(source: Source): Boolean {
        return _favoriteFlow.value[source] ?: false
    }
}