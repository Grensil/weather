package com.example.weather

sealed class MainRoute(val route: String) {

    companion object {
        private const val HOME_ROUTE = "home"
        private const val BOOKMARK_ROUTE = "bookmark"
    }

    object HomeScreen : MainRoute(route = HOME_ROUTE)
    object BookmarkScreen : MainRoute(route = BOOKMARK_ROUTE)
}