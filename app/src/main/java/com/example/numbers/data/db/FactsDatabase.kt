package com.example.numbers.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.numbers.data.NumberFact

@Database(entities = [NumberFact::class], version = 1, exportSchema = false)
abstract class FactsDatabase : RoomDatabase() {

    companion object {
        private val LOCK = Any()
        private const val DB_NAME = "facts"
        private var db: FactsDatabase? = null

        fun getInstance(context: Context): FactsDatabase {
            synchronized(LOCK) {
                db?.let {
                    return it
                }
                val instance = Room.databaseBuilder(
                    context,
                    FactsDatabase::class.java,
                    DB_NAME
                ).build()
                db = instance
                return instance
            }
        }
    }

    abstract fun factsDao(): FactsDao
}