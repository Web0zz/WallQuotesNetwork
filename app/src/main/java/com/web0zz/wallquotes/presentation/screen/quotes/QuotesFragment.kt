package com.web0zz.wallquotes.presentation.screen.quotes

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
import com.web0zz.wallquotes.presentation.screen.quotes.quote.QuoteFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@AndroidEntryPoint
class QuotesFragment : BaseFragment<FragmentQuotesBinding, QuotesViewModel>(
    FragmentQuotesBinding::inflate
) {
    override val mViewModel: QuotesViewModel by viewModels()
    private val safeArgs: QuotesFragmentArgs by navArgs()

    private lateinit var selectedCategory: String
    private lateinit var viewPager2: ViewPager2

    override fun onStartInvoke() {
        setQuotes(selectedCategory)
    }

    override fun onCreateInvoke() {
        selectedCategory = safeArgs.selectedCategory

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
        mViewModel.getByCategory(selectedCategory)
    }

    // Handle HomeUiState

    private fun handleLoading() {
        // TODO set loading state
    }

    private fun handleQuotes(quotes: List<Quotes>) {
        val pagerAdapter =
            QuotesSlidePagerAdapter(this, quotes.size)
            { QuoteFragment.newInstance(quotes[it])}

        viewPager2 = fragmentBinding.quotesViewPager2

        viewPager2.adapter = pagerAdapter
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.UnknownError -> renderFailure()
        }
    }

    // Render Data

    private fun renderFailure(/*@StringRes message: Int*/) {
        // TODO show error to user
    }
}