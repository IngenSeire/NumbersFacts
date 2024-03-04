package com.example.numbers.data.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FactsApiFactory {
    private const val BASE_URL = "http://numbersapi.com/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: FactsService by lazy {
        retrofit.create(FactsService::class.java)
    }
}