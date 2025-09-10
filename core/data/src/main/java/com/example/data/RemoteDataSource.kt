package com.example.data

import com.example.data.model.Article
import com.example.data.model.HeadlineResponse
import com.example.data.model.Source
import com.example.domain.ArticleDto
import com.grensil.network.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject

class RemoteDataSource {
    private val httpClient by lazy { HttpClient() }

    fun getHeadLineArticles(country: String, category: String): Flow<HeadlineResponse> = flow {
        val url = ApiConfig.buildTopHeadlinesUrl(country, category)

        try {
            if (url.isBlank()) {
                throw IllegalArgumentException("URL cannot be blank")
            }
            val headers = mapOf(
                "Accept" to "application/json", "User-Agent" to "NHN-Assignment-App/1.0"
            )
            val response = httpClient.get(url, headers, 30000)
            val headlineResponse = parseHeadlineResponse(response.body)
            emit(headlineResponse)

        } catch (e: Exception) {
            throw e
        }
    }.flowOn(Dispatchers.IO)

    fun parseHeadlineResponse(jsonString: String): HeadlineResponse {
        val status = extractStringValue(jsonString, "status") ?: ""
        val totalResults = extractIntValue(jsonString, "totalResults") ?: 0
        val articles = parseArticlesArray(jsonString)

        return HeadlineResponse(status, totalResults, articles)
    }

    private fun parseArticlesArray(jsonString: String): List<Article> {
        val articlesPattern = """"articles"\s*:\s*\[(.*)]""".toRegex(RegexOption.DOT_MATCHES_ALL)
        val articlesArrayContent = articlesPattern.find(jsonString)?.groupValues?.get(1) ?: return emptyList()
        
        val articles = mutableListOf<Article>()
        val articlePattern = """\{[^{}]*(?:\{[^{}]*\}[^{}]*)*\}""".toRegex()
        
        articlePattern.findAll(articlesArrayContent).forEach { match ->
            val articleJson = match.value
            articles.add(parseArticle(articleJson))
        }
        
        return articles
    }

    private fun parseArticle(articleJson: String): Article {
        val sourceId = extractNestedStringValue(articleJson, "source", "id")
        val sourceName = extractNestedStringValue(articleJson, "source", "name") ?: ""
        val source = Source(id = sourceId, name = sourceName)

        return Article(
            source = source,
            author = extractStringValue(articleJson, "author"),
            title = extractStringValue(articleJson, "title") ?: "",
            description = extractStringValue(articleJson, "description"),
            url = extractStringValue(articleJson, "url") ?: "",
            urlToImage = extractStringValue(articleJson, "urlToImage"),
            publishedAt = extractStringValue(articleJson, "publishedAt") ?: "",
            content = extractStringValue(articleJson, "content")
        )
    }

    private fun extractStringValue(json: String, key: String): String? {
        val pattern = """"$key"\s*:\s*"([^"]*)"""".toRegex()
        val match = pattern.find(json)
        return match?.groupValues?.get(1)?.takeIf { it.isNotEmpty() }
    }

    private fun extractIntValue(json: String, key: String): Int? {
        val pattern = """"$key"\s*:\s*(\d+)""".toRegex()
        val match = pattern.find(json)
        return match?.groupValues?.get(1)?.toIntOrNull()
    }

    private fun extractNestedStringValue(json: String, parentKey: String, childKey: String): String? {
        val parentPattern = """"$parentKey"\s*:\s*\{([^}]*)\}""".toRegex()
        val parentMatch = parentPattern.find(json) ?: return null
        val parentContent = parentMatch.groupValues[1]
        return extractStringValue(parentContent, childKey)
    }
}