package com.example.data

import com.example.data.model.Source
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class LocalDataSource {

    // String key 기반 북마크 관리 (Source 객체 중복 문제 해결)
    private val _sourceStorage = MutableStateFlow<Map<String, Source>>(emptyMap())
    private val _bookmarkStatus = MutableStateFlow<Map<String, Boolean>>(emptyMap())

    private fun getSourceKey(source: Source): String {
        return source.id ?: source.name
    }

    fun getFavoriteList(): Flow<Set<Source>> = _bookmarkStatus.asStateFlow()
        .map { bookmarkMap ->
            bookmarkMap.filter { it.value }
                .keys.mapNotNull { key ->
                    _sourceStorage.value[key]
                }.toSet()
        }

    fun addFavorite(source: Source) {
        val key = getSourceKey(source)
        _sourceStorage.value = _sourceStorage.value + (key to source)
        _bookmarkStatus.value = _bookmarkStatus.value + (key to true)
    }

    fun removeFavorite(source: Source) {
        val key = getSourceKey(source)
        _bookmarkStatus.value = _bookmarkStatus.value + (key to false)
    }

    fun isBookmarked(source: Source): Boolean {
        val key = getSourceKey(source)
        return _bookmarkStatus.value[key] ?: false
    }
}