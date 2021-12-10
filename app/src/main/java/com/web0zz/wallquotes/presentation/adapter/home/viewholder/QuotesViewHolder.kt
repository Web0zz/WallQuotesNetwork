package com.web0zz.wallquotes.presentation.adapter.home.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.wallquotes.databinding.ViewHomeQuoteItemBinding
import com.web0zz.wallquotes.domain.model.Quotes

class QuotesViewHolder(
    private val binding: ViewHomeQuoteItemBinding,
    private val onClickUpdate: (Quotes) -> Unit,
    private val onClickShare: (String) -> Unit,
    private val onClickLike: (Quotes) -> Unit,
    private val onClickDelete: (Quotes) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(quote: Quotes) {
        binding.quote = quote
        binding.itemUpdateImageButton.setOnClickListener { onClickUpdate(quote) }
        binding.itemShareImageButton.setOnClickListener { onClickShare(quote.body) }
        binding.itemLikeImageButton.setOnClickListener { onClickLike(quote) }

        if (quote.tag == "yours") {
            binding.itemDeleteImageButton.visibility = View.VISIBLE
            binding.itemDeleteImageButton.setOnClickListener { onClickDelete(quote) }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClickUpdate: (Quotes) -> Unit,
            onClickShare: (String) -> Unit,
            onClickLike: (Quotes) -> Unit,
            onClickDelete: (Quotes) -> Unit
        ): QuotesViewHolder {
            val view = ViewHomeQuoteItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return QuotesViewHolder(view, onClickUpdate, onClickShare, onClickLike, onClickDelete)
        }
    }
}