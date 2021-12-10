package com.web0zz.wallquotes.presentation.adapter.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.adapter.home.viewholder.QuotesViewHolder

class QuotesRecyclerAdapter(
    private val quotesList: List<Quotes>,
    private val onClickUpdate: (Quotes) -> Unit,
    private val onClickShare: (String) -> Unit,
    private val onClickLike: (Quotes) -> Unit,
    private val onClickDelete: (Quotes) -> Unit
) : RecyclerView.Adapter<QuotesViewHolder>() {
    override fun getItemCount() = quotesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        return QuotesViewHolder.create(
            parent,
            onClickUpdate,
            onClickShare,
            onClickLike,
            onClickDelete
        )
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(quotesList[position])
    }
}