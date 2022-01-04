package com.web0zz.wallquotes.presentation.adapter.home.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.wallquotes.databinding.ViewHomeTagItemBinding
import com.web0zz.wallquotes.domain.model.Tag

class TagViewHolder(
    private val binding: ViewHomeTagItemBinding,
    private val onClickTag: (String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(tag: Tag) {
        binding.tagName = tag.title
        binding.root.setOnClickListener { onClickTag(tag.title) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClickCategory: (String) -> Unit,
        ): TagViewHolder {
            val view = ViewHomeTagItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return TagViewHolder(view, onClickCategory)
        }
    }
}
