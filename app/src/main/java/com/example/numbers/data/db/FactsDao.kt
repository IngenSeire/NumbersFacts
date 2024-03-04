package com.example.numbers.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.numbers.data.NumberFact

@Dao
interface FactsDao {

    @Query("SELECT * FROM facts")
    suspend fun getAllSavedFacts(): List<NumberFact>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveFact(fact: NumberFact)
}