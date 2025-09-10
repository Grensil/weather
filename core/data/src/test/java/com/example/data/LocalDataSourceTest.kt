package com.example.data

import com.example.data.model.Source
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class LocalDataSourceTest {

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        localDataSource = LocalDataSource()
    }

    @Test
    fun `북마크 추가 테스트`() = runBlocking {
        val source1 = Source(id ="id1", name = "name1")
        val source2 = Source(id ="id2", name = "name2")
        val source3 = Source(id ="id3", name = "name3")

        localDataSource.addFavorite(source = source1)
        localDataSource.addFavorite(source = source2)
        localDataSource.addFavorite(source = source2)

        assertEquals(2,localDataSource.getFavoriteList().first().size)

        localDataSource.removeFavorite(source = source1)
        localDataSource.removeFavorite(source = source3)

        assertEquals(1,localDataSource.getFavoriteList().first().size)

        localDataSource.removeFavorite(source = source2)
        assertEquals(0,localDataSource.getFavoriteList().first().size)
    }
}