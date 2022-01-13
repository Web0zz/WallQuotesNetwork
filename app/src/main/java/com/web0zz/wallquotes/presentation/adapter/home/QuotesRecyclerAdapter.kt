package com.web0zz.wallquotes.presentation.adapter.home

import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.adapter.home.viewholder.QuotesViewHolder

class QuotesRecyclerAdapter(
    private val onClickUpdate: (Quotes) -> Unit,
    private val onClickShare: (String) -> Unit,
    private val onClickLike: (Quotes) -> Unit,
    private val onClickDelete: (Quotes) -> Unit,
) : RecyclerView.Adapter<QuotesViewHolder>() {
    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun getItemCount() = differ.currentList.size

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
        holder.bind(differ.currentList[position])
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Quotes>() {
            override fun areItemsTheSame(oldItem: Quotes, newItem: Quotes) = oldItem == newItem
            override fun areContentsTheSame(oldItem: Quotes, newItem: Quotes) =
                oldItem.id == newItem.id
        }
    }
}
