package com.web0zz.wallquotes.presentation.adapter.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.wallquotes.databinding.ViewHomeQuotesListItemBinding
import com.web0zz.wallquotes.domain.model.Quotes

class QuotesViewHolder(
    private val binding: ViewHomeQuotesListItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(quotes: Quotes) {

    }

    companion object {
        fun create(
            parent: ViewGroup
        ): QuotesViewHolder {
            val view = ViewHomeQuotesListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return QuotesViewHolder(view)
        }
    }
}