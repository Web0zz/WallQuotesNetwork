package com.web0zz.wallquotes.presentation.screen.quotes

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.web0zz.wallquotes.databinding.FragmentQuotesBinding
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.adapter.quotes.QuotesSlidePagerAdapter
import com.web0zz.wallquotes.presentation.base.BaseFragment
import com.web0zz.wallquotes.presentation.screen.quotes.quote.SingleQuoteFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@DelicateCoroutinesApi
@AndroidEntryPoint
class QuotesFragment : BaseFragment<FragmentQuotesBinding, QuotesViewModel>(
    FragmentQuotesBinding::inflate
) {
    override val mViewModel: QuotesViewModel by viewModels()
    private val safeArgs: QuotesFragmentArgs by navArgs()

    private lateinit var selectedCategory: String
    private var isLikedQuotes by Delegates.notNull<Boolean>()
    private lateinit var viewPager2: ViewPager2

    override fun onStartInvoke() {
        if (isLikedQuotes) setLikedQuotes() else setQuotes(selectedCategory)
    }

    override fun onCreateInvoke() {
        safeArgs.selectedCategory?.let { selectedCategory = it }
        isLikedQuotes = safeArgs.isLikedQuotes
    }

    override fun onCreateViewInvoke() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.quotesUiState.collect { handleViewState(it) }
            }
        }
    }

    private fun handleViewState(viewState: QuotesViewModel.QuotesUiState) {
        when (viewState) {
            is QuotesViewModel.QuotesUiState.Loading -> handleLoading()
            is QuotesViewModel.QuotesUiState.Success -> handleQuotes(viewState.data)
            is QuotesViewModel.QuotesUiState.Error -> handleFailure(viewState.failure)
        }
    }

    private fun setQuotes(selectedCategory: String) {
        mViewModel.getByTag(selectedCategory)
    }

    private fun setLikedQuotes() {
        mViewModel.getLikedQuotes()
    }

    private fun handleLoading() {
        // TODO will set loading view later
    }

    private fun handleQuotes(quotes: List<Quotes>) {
        val pagerAdapter =
            QuotesSlidePagerAdapter(this, quotes.size) {
                SingleQuoteFragment.newInstance(quotes[it])
            }

        viewPager2 = fragmentBinding.quotesViewPager2

        viewPager2.adapter = pagerAdapter
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.UnknownError -> showFailureText(failure.message, failure.exceptionMessage)
        }
    }

    private fun showFailureText(message: String, exceptionMessage: String?) {
        Log.e("ERROR", "Error on Home: $exceptionMessage")
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
