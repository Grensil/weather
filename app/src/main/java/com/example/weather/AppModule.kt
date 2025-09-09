package com.example.weather

import com.example.data.LocalDataSource
import com.example.data.MainRepositoryImpl
import com.example.data.RemoteDataSource
import com.example.domain.MainRepository
import com.example.domain.MainUseCase

class AppModule private constructor() {

    private val localDataSource: LocalDataSource by lazy {
        LocalDataSource()
    }

    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSource()
    }

    private val repository: MainRepository by lazy {
        MainRepositoryImpl(localDataSource, remoteDataSource)
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