package com.web0zz.wallquotes.presentation.adapter.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.wallquotes.domain.model.Tag
import com.web0zz.wallquotes.presentation.adapter.home.viewholder.TagViewHolder

class TagRecyclerAdapter(
    private val tags: List<Tag>,
    private val onClickTag: (String) -> Unit
) : RecyclerView.Adapter<TagViewHolder>() {
    override fun getItemCount() = tags.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder.create(parent, onClickTag)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags[position])
    }
}