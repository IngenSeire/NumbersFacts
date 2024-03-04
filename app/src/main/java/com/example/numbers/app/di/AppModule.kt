package com.example.numbers.app.di

import com.example.numbers.app.presentation.FactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<FactsViewModel>() {
        FactsViewModel(get(), get())
    }
}