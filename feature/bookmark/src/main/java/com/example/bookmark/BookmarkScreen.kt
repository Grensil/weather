package com.example.bookmark

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookmarkScreen(viewModel: BookmarkViewModel) {

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
                Text(text = "Bookmark Screen")
            }
        })
}