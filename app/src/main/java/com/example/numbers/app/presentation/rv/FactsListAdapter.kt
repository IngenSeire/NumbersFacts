package com.example.numbers.app.presentation.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.numbers.data.NumberFact
import com.example.numbers.databinding.RvItemFactBinding

class FactsListAdapter :
    ListAdapter<NumberFact, FactViewHolder>(FactsDiffCallback) {

    var onFactClickListener: ((NumberFact) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val binding = RvItemFactBinding.inflate(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
        return FactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        val numberFact = getItem(position)
        holder.itemView.setOnClickListener {
            onFactClickListener?.invoke(numberFact)
        }
        holder.binding.tvNumber.text = numberFact.number.toString()
        holder.binding.tvFactOneLine.text = numberFact.fact
    }
}