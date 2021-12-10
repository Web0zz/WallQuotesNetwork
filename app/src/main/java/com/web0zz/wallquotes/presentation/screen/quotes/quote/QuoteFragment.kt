package com.web0zz.wallquotes.presentation.screen.quotes.quote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.web0zz.wallquotes.databinding.ViewQuotesItemBinding
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.base.BaseSimpleFragment

class QuoteFragment : BaseSimpleFragment<ViewQuotesItemBinding>(ViewQuotesItemBinding::inflate) {
    private lateinit var selectedQuotes: Quotes

    override fun initCreate() {
        arguments?.apply {
            selectedQuotes = this.getParcelable(CURRENT_QUOTE)!!
        }
    }

    override fun initUi() {
        fragmentDataBinding.quotes = selectedQuotes
    }

    companion object {
        private const val CURRENT_QUOTE = "quote"

        fun newInstance(quote: Quotes): QuoteFragment =
            QuoteFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CURRENT_QUOTE, quote)
                }
            }
    }
}