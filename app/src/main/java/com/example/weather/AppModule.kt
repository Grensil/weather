package com.example.weather

import com.example.data.LocalDataSource
import com.example.data.MainRepositoryImpl
import com.example.data.RemoteDataSourceImpl
import com.example.domain.MainRepository
import com.example.domain.MainUseCaseImpl

class AppModule private constructor() {

    private val localDataSource: LocalDataSource by lazy {
        LocalDataSource()
    }

    private val remoteDataSourceImpl: RemoteDataSourceImpl by lazy {
        RemoteDataSourceImpl()
    }

    private val repository: MainRepository by lazy {
        MainRepositoryImpl(localDataSource, remoteDataSourceImpl)
    }

    private val _useCase: MainUseCaseImpl by lazy {
        MainUseCaseImpl(repository)
    }

    fun getUseCase(): MainUseCaseImpl = _useCase

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