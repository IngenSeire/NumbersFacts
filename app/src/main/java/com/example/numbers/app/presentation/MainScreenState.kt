package com.example.numbers.app.presentation

import com.example.numbers.data.NumberFact

sealed class MainScreenState {

    object Initial : MainScreenState()

    object Error : MainScreenState()

    class HistoryLoaded(val facts: List<NumberFact>) : MainScreenState()

    class DetailedInfo(val fact: NumberFact) : MainScreenState()
}
