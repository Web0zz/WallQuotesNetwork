package com.web0zz.wallquotes.presentation.adapter.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.wallquotes.databinding.ViewHomeQuoteItemBinding
import com.web0zz.wallquotes.domain.model.Quotes

class QuotesViewHolder(
    private val binding: ViewHomeQuoteItemBinding,
    onClickQuotes: (Quotes) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(quote: Quotes) {
        binding.quote = quote
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClickQuotes: (Quotes) -> Unit
        ): QuotesViewHolder {
            val view = ViewHomeQuoteItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return QuotesViewHolder(view, onClickQuotes)
        }
    }
}