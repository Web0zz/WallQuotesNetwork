package com.web0zz.wallquotes.presentation.screen.quotes.quote

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.ViewQuotesItemBinding
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.base.BaseFragment
import com.web0zz.wallquotes.presentation.base.BaseSimpleFragment
import com.web0zz.wallquotes.presentation.screen.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@AndroidEntryPoint
class SingleQuoteFragment : BaseFragment<ViewQuotesItemBinding, SingleQuoteViewModel>(ViewQuotesItemBinding::inflate) {
    override val mViewModel: SingleQuoteViewModel by viewModels()
    private lateinit var selectedQuotes: Quotes

    override fun onCreateInvoke() {
        arguments?.apply {
            selectedQuotes = this.getParcelable(CURRENT_QUOTE)!!
        }
    }

    override fun onCreateViewInvoke() {
        fragmentBinding.quotes = selectedQuotes

        fragmentBinding.quotesLikeImageButton.setOnClickListener {
            likeQuote(selectedQuotes)
            if (selectedQuotes.tag == "yours") {
                fragmentBinding.quotesLikeImageButton.setImageResource(R.drawable.ic_selected_like)
            } else fragmentBinding.quotesLikeImageButton.setImageResource(R.drawable.ic_unselected_like)
        }

        fragmentBinding.quotesShareImageButton.setOnClickListener {
            shareQuote(selectedQuotes.body)
        }
    }

    private fun shareQuote(body: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, body)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun likeQuote(quotes: Quotes) {
        if (quotes.tag != "yours") {
            val likedQuote = Quotes(
                quotes.id,
                quotes.body,
                quotes.authorName,
                "yours"
            )

            mViewModel.updateQuote(likedQuote)
        } else {
            val likedQuote = Quotes(
                quotes.id,
                quotes.body,
                quotes.authorName,
                "live" // TODO temp solution
            )

            mViewModel.updateQuote(likedQuote)
        }
    }

    companion object {
        private const val CURRENT_QUOTE = "quote"

        fun newInstance(quote: Quotes): SingleQuoteFragment =
            SingleQuoteFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CURRENT_QUOTE, quote)
                }
            }
    }
}