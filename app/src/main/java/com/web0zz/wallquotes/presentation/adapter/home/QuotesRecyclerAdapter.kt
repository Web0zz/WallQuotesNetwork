package com.web0zz.wallquotes.presentation.adapter.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.adapter.home.viewholder.QuotesViewHolder

class QuotesRecyclerAdapter(
    private val quotesList: List<Quotes>
) : RecyclerView.Adapter<QuotesViewHolder>() {
    override fun getItemCount() = quotesList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        return QuotesViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        holder.bind(quotesList[position])
    }
}