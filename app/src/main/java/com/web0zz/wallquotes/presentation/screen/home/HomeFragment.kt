package com.web0zz.wallquotes.presentation.screen.home

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.FragmentHomeBinding
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Tag
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.adapter.home.TagRecyclerAdapter
import com.web0zz.wallquotes.presentation.adapter.home.QuotesRecyclerAdapter
import com.web0zz.wallquotes.presentation.base.BaseFragment
import com.web0zz.wallquotes.presentation.util.FragmentUtil.getFragmentNavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val mViewModel: HomeViewModel by viewModels()
    private val navController by lazy {
        getFragmentNavController(R.id.nav_host_fragmentContainerView)
    }

    override fun onCreateInvoke() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.homeTagUiState.collect { handleTagViewState(it) }
                mViewModel.homeQuotesUiState.collect { handleQuotesViewState(it) }
            }
        }
    }

    private fun handleQuotesViewState(viewState: HomeViewModel.HomeUiState) {
        when (viewState) {
            is HomeViewModel.HomeUiState.Loading -> handleLoading()
            is HomeViewModel.HomeUiState.Success -> handleQuotesData(viewState.quotesData!!)
            is HomeViewModel.HomeUiState.Error -> handleFailure(viewState.failure)
        }
    }

    private fun handleTagViewState(viewState: HomeViewModel.HomeUiState) {
        when (viewState) {
            is HomeViewModel.HomeUiState.Loading -> handleLoading()
            is HomeViewModel.HomeUiState.Success -> handleTagData(viewState.tagData!!)
            is HomeViewModel.HomeUiState.Error -> handleFailure(viewState.failure)
        }
    }

    private fun loadQuotesList() {
        mViewModel.getAllQuotes()
    }

    private fun loadTagList() {
        mViewModel.getAllCategory()
    }

    private fun navigateToQuotes(selectedTagTitle: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToQuotesFragment(selectedTagTitle)
        navController?.navigate(action)
    }

    private fun navigateToEdit(quotes: Quotes?) {
        val action = HomeFragmentDirections.actionHomeFragmentToEditorFragment(quotes)
        navController?.navigate(action)
    }

    // Handle HomeUiState

    private fun handleLoading() {
        // TODO set loading state
    }

    private fun handleQuotesData(quotes: List<Quotes>) {
        fragmentBinding.quotesRecyclerView.apply {
            adapter = QuotesRecyclerAdapter(quotes, ::navigateToEdit)
        }
    }

    private fun handleTagData(tags: List<Tag>) {
        fragmentBinding.tagRecyclerView.apply {
            adapter = TagRecyclerAdapter(tags, ::navigateToQuotes)
        }
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.UnknownError -> showFailureText(failure.message, failure.exceptionMessage)
        }
    }

    // Render Data

    private fun showFailureText(message: String, exceptionMessage: String?) {
        Log.e("ERROR","Error on Login: $exceptionMessage")
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}