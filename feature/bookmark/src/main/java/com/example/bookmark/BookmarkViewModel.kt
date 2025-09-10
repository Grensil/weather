package com.example.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.ArticleDto
import com.example.domain.MainUseCase
import com.example.domain.SourceDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class BookmarkViewModel(private val useCase: MainUseCase) : ViewModel() {

    private val _bookmarkList = MutableStateFlow<Set<SourceDto>>(emptySet())
    val bookmarkList = _bookmarkList.asStateFlow()

    init {
        getBookmarkList()
    }

    fun getBookmarkList() = viewModelScope.launch {
        useCase.getFavoriteList()
            .distinctUntilChanged()
            .collect { it ->
            _bookmarkList.value = it
        }
    }

    fun addFavorite(sourceDto: SourceDto) {
        useCase.addFavorite(sourceDto)
    }

    fun removeFavorite(sourceDto: SourceDto) {
        useCase.removeFavorite(sourceDto)
    }

    fun isBookmarked(sourceDto: SourceDto) : Boolean {
        return useCase.isBookmarked(sourceDto)
    }
}