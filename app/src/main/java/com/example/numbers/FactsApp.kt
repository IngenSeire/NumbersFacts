package com.example.numbers

import android.app.Application
import com.example.numbers.app.di.appModule
import com.example.numbers.app.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FactsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FactsApp)
            modules(modules = listOf(dataModule, appModule))
        }
    }
}