package com.example.data

import com.grensil.network.HttpClient

class LocalDataSource {
    private val httpClient: HttpClient by lazy { HttpClient() }
}