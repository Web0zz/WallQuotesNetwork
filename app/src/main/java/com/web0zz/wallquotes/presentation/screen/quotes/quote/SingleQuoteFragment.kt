package com.web0zz.wallquotes.presentation.screen.quotes.quote

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.ViewQuotesItemBinding
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.MainActivity
import com.web0zz.wallquotes.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@AndroidEntryPoint
class SingleQuoteFragment : BaseFragment<ViewQuotesItemBinding>(ViewQuotesItemBinding::inflate) {
    private val mViewModel: SingleQuoteViewModel by viewModels()
    override val progressBar: View by lazy {
        (requireActivity() as MainActivity).progressBar
    }

    private lateinit var selectedQuotes: Quotes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            selectedQuotes = this.getParcelable(CURRENT_QUOTE)!!
        }
    }

    override fun onCreateViewInvoke() {
        fragmentBinding.quotes = selectedQuotes
        setLikeImage()

        fragmentBinding.quotesLikeImageButton.setOnClickListener {
            likeQuote(selectedQuotes)
            setLikeImage()
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

    private fun setLikeImage() {
        if (selectedQuotes.isLiked) {
            fragmentBinding.quotesLikeImageButton.setImageResource(R.drawable.ic_selected_like)
        } else fragmentBinding.quotesLikeImageButton.setImageResource(R.drawable.ic_unselected_like)
    }

    private fun likeQuote(quotes: Quotes) {
        if (quotes.isLiked) {
            quotes.isLiked = false

            val likedQuote = Quotes(
                quotes.id,
                quotes.body,
                quotes.authorName,
                quotes.tag,
                false
            )

            Toast.makeText(context, "Quote UnLiked", Toast.LENGTH_SHORT).show()
            mViewModel.updateQuote(likedQuote)
        } else if (!quotes.isLiked) {
            quotes.isLiked = true

            val unlikedQuote = Quotes(
                quotes.id,
                quotes.body,
                quotes.authorName,
                quotes.tag,
                true
            )

            Toast.makeText(context, "Quote Liked", Toast.LENGTH_SHORT).show()
            mViewModel.updateQuote(unlikedQuote)
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
