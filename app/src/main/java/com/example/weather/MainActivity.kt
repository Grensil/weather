package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.home.HomeScreen
import com.example.home.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        val appModule = MyApplication().getAppModule()
//        val viewModel: HomeViewModel = ViewModelProvider(this,
//            factory = createViewModelFactory { HomeViewModel(appModule.getUseCase()) }
//        )[HomeViewModel::class.java]
//
//        setContent {
//            HomeScreen(viewModel)
//        }
        setContent {
            ManScreen()
        }
    }
}
