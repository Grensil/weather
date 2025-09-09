package com.example.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookmark.BookmarkScreen
import com.example.bookmark.BookmarkViewModel
import com.example.home.HomeScreen
import com.example.home.HomeViewModel

@Composable
fun ManScreen() {
    val navController = rememberNavController()
    Scaffold(bottomBar = {
        BottomNavBar(navController)
    }, content = { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            MainNavGraph(navController)
        }
    })

}

@Composable
fun MainNavGraph(navController: NavHostController) {
    val context = LocalContext.current
    val appModules = (context.applicationContext as MyApplication).getAppModule()
    
    NavHost(navController = navController, startDestination = MainRoute.HomeScreen.route) {

        composable(route = MainRoute.HomeScreen.route) { backStackEntry ->
            val homeViewModel = viewModel<HomeViewModel>(viewModelStoreOwner = backStackEntry,
                factory = createViewModelFactory { HomeViewModel(appModules.getUseCase()) }
            )
            HomeScreen(viewModel = homeViewModel)
        }

        composable(route = MainRoute.BookmarkScreen.route) { backStackEntry ->
            val bookmarkViewModel = viewModel<BookmarkViewModel>(viewModelStoreOwner = backStackEntry,
                factory = createViewModelFactory { BookmarkViewModel() }
            )
            BookmarkScreen(viewModel = bookmarkViewModel)
        }
    }
}