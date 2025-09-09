package com.example.data

object ApiConfig {
    const val BASE_URL = BuildConfig.BASE_URL
    const val API_KEY = BuildConfig.NEWS_API_KEY
    
    object Endpoints {
        const val TOP_HEADLINES = "/top-headlines"
    }
    
    object Params {
        const val COUNTRY = "country"
        const val CATEGORY = "category"
        const val API_KEY_PARAM = "apiKey"
    }
    
    fun buildTopHeadlinesUrl(country: String = "us", category: String = "business"): String {
        return "$BASE_URL${Endpoints.TOP_HEADLINES}?${Params.COUNTRY}=$country&${Params.CATEGORY}=$category&${Params.API_KEY_PARAM}=$API_KEY"
    }
}