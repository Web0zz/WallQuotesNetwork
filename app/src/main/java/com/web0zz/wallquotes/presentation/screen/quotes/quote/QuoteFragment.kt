package com.web0zz.wallquotes.presentation.screen.quotes.quote

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.web0zz.wallquotes.domain.model.Quotes

class QuoteFragment : Fragment() {
    private lateinit var selectedQuotes: Quotes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            selectedQuotes = this.getParcelable(CURRENT_QUOTE)!!
        }
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