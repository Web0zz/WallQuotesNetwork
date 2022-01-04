package com.web0zz.wallquotes.presentation.adapter.home.viewholder

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.ViewHomeQuoteItemBinding
import com.web0zz.wallquotes.domain.model.Quotes

class QuotesViewHolder(
    private val binding: ViewHomeQuoteItemBinding,
    private val onClickUpdate: (Quotes) -> Unit,
    private val onClickShare: (String) -> Unit,
    private val onClickLike: (Quotes) -> Unit,
    private val onClickDelete: (Quotes) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(quote: Quotes) {
        var isClicked = false

        binding.quote = quote

        binding.root.setOnClickListener {
            isClicked = !isClicked

            val animator = ValueAnimator.ofInt(
                binding.quoteConstraintLayout.paddingBottom, if (isClicked) 15 else 0
            )
            animator.addUpdateListener { valueAnimator ->
                binding.quoteConstraintLayout.setPadding(
                    0,
                    0,
                    0,
                    valueAnimator.animatedValue as Int
                )
            }
            animator.duration = 200
            animator.start()

            binding.commonButtonsGroup.visibility = if (isClicked) View.VISIBLE else View.GONE

            if (quote.isLiked) {
                binding.itemLikeImageButton.setImageResource(R.drawable.ic_selected_like)
            } else binding.itemLikeImageButton.setImageResource(R.drawable.ic_unselected_like)

            binding.itemShareImageButton.setOnClickListener { onClickShare(quote.body) }
            binding.itemLikeImageButton.setOnClickListener {
                onClickLike(quote)
                if (quote.isLiked) {
                    quote.isLiked = true
                    binding.itemLikeImageButton.setImageResource(R.drawable.ic_selected_like)
                } else {
                    quote.isLiked = false
                    binding.itemLikeImageButton.setImageResource(R.drawable.ic_unselected_like)
                }
            }

            if (quote.tag == "your") {
                binding.personalButtonsGroup.visibility = if (isClicked) View.VISIBLE else View.GONE

                binding.itemDeleteImageButton.setOnClickListener { onClickDelete(quote) }
                binding.itemUpdateImageButton.setOnClickListener { onClickUpdate(quote) }
            }
        }

        val animationView = AnimationUtils.loadAnimation(
            binding.root.context,
            R.anim.recyclerview_quote_anim
        )
        binding.root.startAnimation(animationView)
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClickUpdate: (Quotes) -> Unit,
            onClickShare: (String) -> Unit,
            onClickLike: (Quotes) -> Unit,
            onClickDelete: (Quotes) -> Unit,
        ): QuotesViewHolder {
            val view = ViewHomeQuoteItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return QuotesViewHolder(view, onClickUpdate, onClickShare, onClickLike, onClickDelete)
        }
    }
}
