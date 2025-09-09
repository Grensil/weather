package com.example.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun BottomNavBar(navController: NavHostController) {

    Row(modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.LightGray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {

        Image(imageVector = Icons.AutoMirrored.Filled.List,
            contentDescription = "list icon",
            modifier = Modifier.weight(1f).clickable {
                navController.navigate(MainRoute.HomeScreen.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })

        Image(imageVector = Icons.Default.Favorite,
            contentDescription = "bookmark icon",
            modifier = Modifier.weight(1f).clickable {
                navController.navigate(MainRoute.BookmarkScreen.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
    }
}

@Composable
@Preview
fun BottomNavBarPreview() {
    val fakeNavController = rememberNavController()
    BottomNavBar(fakeNavController)
}