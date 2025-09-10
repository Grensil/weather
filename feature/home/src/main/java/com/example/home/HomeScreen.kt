package com.example.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.ArticleDto
import com.example.domain.MainRepository
import com.example.domain.MainUseCaseImpl
import com.example.home.component.ArticleItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun HomeScreen(viewModel: HomeViewModel) {

    val articles = viewModel.articles.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp).padding(top = 16.dp)
        ) {
            Text(
                text = "Articles",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }, content = { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(articles.value.size) { index ->
                val item = articles.value[index]
                ArticleItem(item = item)
            }
        }
    })
}

@Preview
@Composable
fun HomeScreenPreview() {
    val fakeRepository = FakeRepository()
    val fakeUseCase = MainUseCaseImpl(fakeRepository)
    val fakeViewModel = HomeViewModel(fakeUseCase)
    HomeScreen(fakeViewModel)
}

class FakeRepository : MainRepository {

    private val fakeArticle = List(10) { ArticleDto() }

    override suspend fun getTopHeadlines(
        country: String,
        category: String
    ): Flow<List<ArticleDto>> = flow {
        emit(fakeArticle)
    }
}