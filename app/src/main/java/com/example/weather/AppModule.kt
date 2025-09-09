package com.example.weather

import com.example.data.MainRepositoryImpl
import com.example.domain.MainRepository
import com.example.domain.MainUseCase

class AppModule {

    private val repository: MainRepository by lazy {
        MainRepositoryImpl()
    }

    private val _useCase: MainUseCase by lazy {
        MainUseCase(repository)
    }

    fun getUseCase(): MainUseCase = _useCase

    companion object {
        @Volatile
        private var INSTANCE: AppModule? = null

        fun getInstance(): AppModule {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppModule().also { INSTANCE = it }
            }
        }
    }
}