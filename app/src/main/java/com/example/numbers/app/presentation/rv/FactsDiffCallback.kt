package com.example.numbers.app.presentation.rv

import androidx.recyclerview.widget.DiffUtil
import com.example.numbers.data.NumberFact

object FactsDiffCallback : DiffUtil.ItemCallback<NumberFact>() {

    override fun areItemsTheSame(oldItem: NumberFact, newItem: NumberFact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NumberFact, newItem: NumberFact): Boolean {
        return oldItem == newItem
    }
}