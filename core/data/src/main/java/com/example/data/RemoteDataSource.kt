package com.example.data

import android.util.Log
import com.example.data.model.Article
import com.example.data.model.HeadlineResponse
import com.example.data.model.Source
import com.example.domain.ArticleDto
import com.grensil.network.HttpClient
import org.json.JSONObject

class RemoteDataSource {
    private val httpClient by lazy { HttpClient() }

   fun getHeadLineArticles(country: String, category: String): List<ArticleDto> {
        val url = ApiConfig.buildTopHeadlinesUrl(country, category)

        try {
            if (url.isBlank()) {
                throw IllegalArgumentException("URL cannot be blank")
            }
            val headers = mapOf(
                "Accept" to "application/json", "User-Agent" to "NHN-Assignment-App/1.0"
            )
            val response = httpClient.get(url, headers, 30000)
            val parseResponse = parseHeadlineResponse(response.body)
            return parseResponse.articles.map { it.toArticleDto() }

        } catch (e: Exception) {
            Log.e("NetworkError", "Full exception: $e")
            throw e
        }
    }

    fun parseHeadlineResponse(jsonString: String): HeadlineResponse {
        val jsonObject = JSONObject(jsonString)

        val status = jsonObject.getString("status")
        val totalResults = jsonObject.getInt("totalResults")

        val articlesArray = jsonObject.getJSONArray("articles")
        val articles = mutableListOf<Article>()

        for (i in 0 until articlesArray.length()) {
            val articleJson = articlesArray.getJSONObject(i)
            articles.add(parseArticle(articleJson))
        }

        return HeadlineResponse(status, totalResults, articles)
    }

    private fun parseArticle(articleJson: JSONObject): Article {
        val sourceJson = articleJson.getJSONObject("source")
        val source = Source(
            id = if (sourceJson.isNull("id")) null else sourceJson.getString("id"),
            name = sourceJson.getString("name")
        )

        return Article(
            source = source,
            author = if (articleJson.isNull("author")) null else articleJson.getString("author"),
            title = articleJson.getString("title"),
            description = if (articleJson.isNull("description")) null else articleJson.getString("description"),
            url = articleJson.getString("url"),
            urlToImage = if (articleJson.isNull("urlToImage")) null else articleJson.getString("urlToImage"),
            publishedAt = articleJson.getString("publishedAt"),
            content = if (articleJson.isNull("content")) null else articleJson.getString("content")
        )
    }
}