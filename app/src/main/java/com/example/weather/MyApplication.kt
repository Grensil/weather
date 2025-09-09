package com.example.weather

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

    fun getAppModule() : AppModule = AppModule.getInstance()
}