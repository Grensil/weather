package com.example.data

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test

import org.junit.Before
import kotlin.collections.first

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DataUnitTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localDataSource: LocalDataSource
    private lateinit var repository: MainRepositoryImpl

    @Before
    fun setup() {
        remoteDataSource = RemoteDataSource()
        localDataSource = LocalDataSource()
        repository = MainRepositoryImpl(localDataSource,remoteDataSource)
    }

    @Test
    fun `아티클_리스트_요청_테스트`() = runBlocking {
        val result = repository.getTopHeadlines("us", "business")

        // Then
        assertTrue("결과가 비어있지 않아야 함", result.first().isNotEmpty())

    }
}