package com.example.data

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Before

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
    fun Article_List_IsValidated() = runBlocking {
        var response = repository.getTopHeadlines(country = "us", category = "business")
        println(response.size)
        response.forEach { println("${it}") }

        assertTrue("article existed", response.size>0)
    }
}