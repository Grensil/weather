package com.example.bookmark

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun BookmarkScreen(viewModel: BookmarkViewModel) {

    val bookmarkList = viewModel.bookmarkList.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            Row(modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp)) {
                Text(
                    text = "Bookmark List",
                    fontSize = 32.sp,
                    modifier = Modifier.padding(vertical = 16.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    itemsIndexed(bookmarkList.value.toList()) { index, item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .padding(horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.name,
                                fontSize = 20.sp,
                                modifier = Modifier.weight(1f)
                            )
                            
                            IconButton(
                                onClick = {
                                    if(viewModel.isBookmarked(item)) {
                                        viewModel.removeFavorite(item)
                                    } else {
                                        viewModel.addFavorite(item)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = if (viewModel.isBookmarked(item))
                                        Icons.Default.Favorite
                                    else Icons.Default.FavoriteBorder,
                                    contentDescription = "bookmark toggle"
                                )
                            }
                        }
                    }
                }
            }
        })
}