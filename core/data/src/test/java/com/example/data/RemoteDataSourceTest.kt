package com.example.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class RemoteDataSourceTest {

    private lateinit var remoteDataSourceImpl: RemoteDataSourceImpl

    @Before
    fun setUp() {
        remoteDataSourceImpl = RemoteDataSourceImpl()
    }

    @Test
    fun `정상적인_JSON_응답을_파싱한다`() {
        // Given
        val jsonResponse = """
            {
                "status": "ok",
                "totalResults": 2,
                "articles": [
                    {
                        "source": {
                            "id": "test-source",
                            "name": "Test Source"
                        },
                        "author": "Test Author",
                        "title": "Test Title",
                        "description": "Test Description",
                        "url": "https://test.com",
                        "urlToImage": "https://test.com/image.jpg",
                        "publishedAt": "2024-01-01T10:00:00Z",
                        "content": "Test Content"
                    },
                    {
                        "source": {
                            "id": null,
                            "name": "Another Source"
                        },
                        "author": null,
                        "title": "Another Title",
                        "description": null,
                        "url": "https://another.com",
                        "urlToImage": null,
                        "publishedAt": "2024-01-02T11:00:00Z",
                        "content": null
                    }
                ]
            }
        """.trimIndent()

        // When
        val result = remoteDataSourceImpl.parseHeadlineResponse(jsonResponse)

        // Then
        assertEquals("ok", result.status)
        assertEquals(2, result.totalResults)
        assertEquals(2, result.articles.size)

        val firstArticle = result.articles[0]
        assertEquals("test-source", firstArticle.source.id)
        assertEquals("Test Source", firstArticle.source.name)
        assertEquals("Test Author", firstArticle.author)
        assertEquals("Test Title", firstArticle.title)
        assertEquals("Test Description", firstArticle.description)
        assertEquals("https://test.com", firstArticle.url)
        assertEquals("https://test.com/image.jpg", firstArticle.urlToImage)
        assertEquals("2024-01-01T10:00:00Z", firstArticle.publishedAt)
        assertEquals("Test Content", firstArticle.content)

        val secondArticle = result.articles[1]
        assertNull("두번째 기사의 source.id는 null이어야 함", secondArticle.source.id)
        assertEquals("Another Source", secondArticle.source.name)
        assertNull("두번째 기사의 author는 null이어야 함", secondArticle.author)
        assertEquals("Another Title", secondArticle.title)
        assertNull("두번째 기사의 description은 null이어야 함", secondArticle.description)
    }

    @Test
    fun `빈_articles_배열을_처리한다`() {
        // Given
        val jsonResponse = """
            {
                "status": "ok",
                "totalResults": 0,
                "articles": []
            }
        """.trimIndent()

        // When
        val result = remoteDataSourceImpl.parseHeadlineResponse(jsonResponse)

        // Then
        assertEquals("ok", result.status)
        assertEquals(0, result.totalResults)
        assertTrue("빈 배열이어야 함", result.articles.isEmpty())
    }

    @Test
    fun `필수_필드가_누락된_경우_기본값을_사용한다`() {
        // Given
        val jsonResponse = """
            {
                "articles": [
                    {
                        "source": {
                            "name": "Test Source"
                        },
                        "title": "Test Title",
                        "url": "https://test.com",
                        "publishedAt": "2024-01-01T10:00:00Z"
                    }
                ]
            }
        """.trimIndent()

        // When
        val result = remoteDataSourceImpl.parseHeadlineResponse(jsonResponse)

        // Then
        assertEquals("", result.status) // 기본값
        assertEquals(0, result.totalResults) // 기본값
        assertEquals(1, result.articles.size)

        val article = result.articles[0]
        assertNull("source.id는 null이어야 함", article.source.id)
        assertEquals("Test Source", article.source.name)
        assertNull("author는 null이어야 함", article.author)
        assertEquals("Test Title", article.title)
        assertNull("description은 null이어야 함", article.description)
        assertEquals("https://test.com", article.url)
        assertNull("urlToImage는 null이어야 함", article.urlToImage)
        assertEquals("2024-01-01T10:00:00Z", article.publishedAt)
        assertNull("content는 null이어야 함", article.content)
    }

    @Test
    fun `잘못된_JSON_형식은_빈_결과를_반환한다`() {
        // Given
        val invalidJson = "{ invalid json }"

        // When
        val result = remoteDataSourceImpl.parseHeadlineResponse(invalidJson)

        // Then
        assertEquals("", result.status)
        assertEquals(0, result.totalResults)
        assertTrue("빈 배열이어야 함", result.articles.isEmpty())
    }

    @Test
    fun `아티클_리스트_응답_테스트`() = runBlocking {
        //Given
        val result = remoteDataSourceImpl.getHeadLineArticles(country = "us", category = "business")

        println("result : ${result.first()}")
        assertEquals(true,result.first().articles.isNotEmpty())
    }
}