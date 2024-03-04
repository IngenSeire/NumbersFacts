package com.example.numbers.app.di

import com.example.numbers.data.db.FactsDao
import com.example.numbers.data.db.FactsDatabase
import com.example.numbers.data.networking.FactsApiFactory
import com.example.numbers.data.networking.FactsService
import org.koin.dsl.module

val dataModule = module {

    single<FactsService> {
        FactsApiFactory.apiService
    }

    single<FactsDao> {
        FactsDatabase.getInstance(get()).factsDao()
    }
}