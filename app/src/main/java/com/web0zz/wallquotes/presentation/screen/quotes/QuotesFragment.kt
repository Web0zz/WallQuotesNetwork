package com.web0zz.wallquotes.presentation.screen.quotes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.web0zz.wallquotes.databinding.FragmentQuotesBinding
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.MainActivity
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
class QuotesFragment : BaseFragment<FragmentQuotesBinding>(
    FragmentQuotesBinding::inflate
) {
    private val mViewModel: QuotesViewModel by viewModels()
    private val safeArgs: QuotesFragmentArgs by navArgs()

    override val progressBar: View = (requireActivity() as MainActivity).progressBar

    private lateinit var selectedCategory: String
    private var isLikedQuotes by Delegates.notNull<Boolean>()
    private lateinit var viewPager2: ViewPager2

    override fun onStart() {
        super.onStart()
        if (isLikedQuotes) setLikedQuotes() else setQuotes(selectedCategory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun handleViewState(uiState: QuotesUiState) {
        setProgressStatus(uiState.isLoading)

        uiState.errorMessage?.let {
            showErrorDialog("Error on Quotes Screen", it)
        }

        handleQuotes(uiState.quoteList)
    }

    private fun setQuotes(selectedCategory: String) {
        mViewModel.getByTag(selectedCategory)
    }

    private fun setLikedQuotes() {
        mViewModel.getLikedQuotes()
    }

    private fun handleQuotes(quotes: List<Quotes>) {
        val pagerAdapter =
            QuotesSlidePagerAdapter(this, quotes.size) {
                SingleQuoteFragment.newInstance(quotes[it])
            }

        viewPager2 = fragmentBinding.quotesViewPager2
        viewPager2.adapter = pagerAdapter

        TabLayoutMediator(fragmentBinding.indicatorTabLayout, fragmentBinding.quotesViewPager2)
        { _, _ -> }.attach()
    }
}
