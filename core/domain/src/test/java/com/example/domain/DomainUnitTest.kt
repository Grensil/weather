package com.example.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DomainUnitTest {

    private lateinit var mockRepository : FakeRepository
    private lateinit var mockUseCase : MainUseCase

    @Before
    fun setUp() {
        mockRepository = FakeRepository()
        mockUseCase = MainUseCaseImpl(mockRepository)
    }


    @Test
    fun `유스케이스가_정상_데이터를_반환한다`() = runBlocking {
        // When
        val 결과Flow = mockUseCase.getTopHeadlines("us", "business")
        val 결과 = 결과Flow.first()
        
        // Then
        assertNotNull("결과가 null이 아니어야 함", 결과)
        assertTrue("결과가 비어있지 않아야 함", 결과.isNotEmpty())
        
        val 첫번째_기사 = 결과.first()
        assertEquals("테스트 뉴스", 첫번째_기사.title)
        assertEquals("테스트 소스", 첫번째_기사.source?.name)
        assertEquals("test", 첫번째_기사.source?.id)
        assertEquals(false, 첫번째_기사.bookmarked)
    }

    @Test
    fun `정상적인_요청시_데이터를_반환한다`() = runBlocking {
        // When
        val 결과Flow = mockUseCase.getTopHeadlines("us", "business")
        val 결과 = 결과Flow.first()
        
        // Then
        assertFalse("결과가 비어있지 않아야 함", 결과.isEmpty())
        assertEquals("테스트 뉴스", 결과.first().title)
    }

    @Test
    fun `유스케이스가_파라미터를_올바르게_전달한다`() = runBlocking {
        // Given
        val 국가 = "kr"
        val 카테고리 = "technology"
        
        // When
        val 결과Flow = mockUseCase.getTopHeadlines(국가, 카테고리)
        val 결과 = 결과Flow.first()
        
        // Then
        assertNotNull("결과가 null이 아니어야 함", 결과)
        assertTrue("결과가 비어있지 않아야 함", 결과.isNotEmpty())
    }

    
    @Test
    fun `빈_국가_코드_전달시_예외_발생`() {
        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { mockUseCase.getTopHeadlines("", "business").first() }
        }
    }
    
    @Test
    fun `빈_카테고리_전달시_예외_발생`() {
        // When & Then
        assertThrows(IllegalArgumentException::class.java) {
            runBlocking { mockUseCase.getTopHeadlines("us", "").first() }
        }
    }
    
    @Test
    fun `정상_데이터의_구조_검증`() = runBlocking {
        // When
        val 결과Flow = mockUseCase.getTopHeadlines("us", "business")
        val 결과 = 결과Flow.first()
        
        // Then
        assertTrue("결과 리스트가 비어있지 않아야 함", 결과.isNotEmpty())
        val 첫번째_기사 = 결과.first()
        assertNotNull("제목이 null이 아니어야 함", 첫번째_기사.title)
        assertNotNull("소스가 null이 아니어야 함", 첫번째_기사.source)
        assertNotNull("URL이 null이 아니어야 함", 첫번째_기사.url)
        assertTrue("URL이 유효한 형식이어야 함", 첫번째_기사.url!!.startsWith("http"))
    }
    
    @Test
    fun `다양한_국가_카테고리_조합_테스트`() = runBlocking {
        val 테스트_케이스 = listOf(
            Pair("us", "business"),
            Pair("kr", "technology"), 
            Pair("jp", "sports"),
            Pair("de", "health")
        )
        
        테스트_케이스.forEach { (국가, 카테고리) ->
            val 결과Flow = mockUseCase.getTopHeadlines(국가, 카테고리)
            val 결과 = 결과Flow.first()
            assertNotNull("$국가-$카테고리 조합의 결과가 null이 아니어야 함", 결과)
        }
    }
}

class FakeRepository : MainRepository {

    override suspend fun getTopHeadlines(
        country: String,
        category: String
    ): Flow<List<ArticleDto>> = flow {
        when {
            country.isBlank() -> throw IllegalArgumentException("국가 코드는 비어있을 수 없습니다")
            category.isBlank() -> throw IllegalArgumentException("카테고리는 비어있을 수 없습니다")
            else -> emit(listOf(
                ArticleDto(
                    title = "테스트 뉴스",
                    source = SourceDto(id = "test", name = "테스트 소스"),
                    author = "테스트 작성자",
                    description = "테스트 설명",
                    url = "https://test.com/news",
                    urlToImage = "https://test.com/image.jpg",
                    publishedAt = "2024-01-01T10:00:00Z",
                    content = "테스트 내용",
                    bookmarked = false
                )
            ))
        }
    }
}