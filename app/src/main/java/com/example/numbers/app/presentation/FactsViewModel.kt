package com.example.numbers.app.presentation

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.numbers.data.NumberFact
import com.example.numbers.data.db.FactsDao
import com.example.numbers.data.networking.FactsService
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FactsViewModel(
    private val factsService: FactsService,
    private val factsDao: FactsDao
) : ViewModel() {

    init {
        loadSavedFacts()
    }

    private val _screenState = MutableLiveData<MainScreenState>(MainScreenState.Initial)
    val screenState: LiveData<MainScreenState> = _screenState

    fun getDetailedFactInfo(fact: NumberFact) {
        _screenState.value = MainScreenState.DetailedInfo(fact)
    }

    private fun loadSavedFacts() {
        viewModelScope.launch {
            val savedList = factsDao.getAllSavedFacts().sortedByDescending { fact -> fact.id }
            _screenState.postValue(MainScreenState.HistoryLoaded(savedList))
        }
    }

    private fun saveFact(fact: NumberFact) {
        viewModelScope.launch {
            factsDao.saveFact(fact = fact)
        }
    }

    private fun loadNewFact(number: Int) {
        viewModelScope.launch {
            try {
                val result: Call<ResponseBody> = factsService.getFact(number)
                result.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        val text = response.body()?.string() ?: ""
                        if (text.isEmpty()) return
                        val fact = NumberFact(number = number, fact = text)
                        saveFact(fact)
                        loadSavedFacts()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        _screenState.value = MainScreenState.Error
                    }
                })
            } catch (e: Exception) {
                _screenState.value = MainScreenState.Error
            }
        }
    }

    fun loadNewFactRandom() {
        viewModelScope.launch {
            try {
                val result: Call<ResponseBody> = factsService.getFactRandom()
                result.enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        val text = response.body()?.string() ?: ""
                        val number =
                            Integer.parseInt(text.subSequence(0, text.indexOf(" ")).toString())
                        //теоретично не найкраще рішення так отримувати число, але оскільки відповідь приходить в форматі x is ... це цілком працює
                        if (text.isEmpty()) return
                        val fact = NumberFact(number = number, fact = text)
                        saveFact(fact)
                        loadSavedFacts()
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        _screenState.value = MainScreenState.Error
                    }
                })
            } catch (e: Exception) {
                _screenState.value = MainScreenState.Error
            }
        }
    }

    fun checkInputAndLoadFact(input: Editable) {
        if (input.isNotBlank())
            loadNewFact(Integer.parseInt(input.toString().replace(" ", "")))
    }
}