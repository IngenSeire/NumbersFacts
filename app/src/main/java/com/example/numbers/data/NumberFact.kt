package com.example.numbers.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "facts")
@Parcelize
data class NumberFact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val number: Int,
    val fact: String
) : Parcelable
