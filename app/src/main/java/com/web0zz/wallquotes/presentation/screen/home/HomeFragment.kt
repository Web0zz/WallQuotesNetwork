package com.web0zz.wallquotes.presentation.screen.home

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.FragmentHomeBinding
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.model.Tag
import com.web0zz.wallquotes.presentation.adapter.home.QuotesRecyclerAdapter
import com.web0zz.wallquotes.presentation.adapter.home.TagRecyclerAdapter
import com.web0zz.wallquotes.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val mViewModel: HomeViewModel by viewModels()
    private val navController by lazy {
        activity?.let {
            Navigation.findNavController(it, R.id.nav_host_fragmentContainerView)
        }
    }

    override fun onCreateInvoke() {
        setHasOptionsMenu(true)
        loadTagList()
        loadQuotesList()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.setGroupVisible(R.id.home_menu_group, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.liked_quote -> {
                navigateToQuotes(null, true)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateViewInvoke() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    mViewModel.homeQuotesUiState.collect { handleQuotesViewState(it) }
                }

                launch {
                    mViewModel.homeTagUiState.collect { handleTagViewState(it) }
                }
            }
        }

        fragmentBinding.homeAddQuoteFloatingActionButton.setOnClickListener {
            navigateToEdit(null)
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
        mViewModel.getAllTag()
    }

    private fun deleteQuote(quotes: Quotes) {
        mViewModel.deleteQuote(quotes)
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
        if (quotes.isLiked) {
            val likedQuote = Quotes(
                quotes.id,
                quotes.body,
                quotes.authorName,
                quotes.tag,
                false
            )

            Toast.makeText(context, "Quote UnLiked", Toast.LENGTH_SHORT).show()
            mViewModel.updateLikeQuote(likedQuote)
        } else if (!quotes.isLiked) {
            val unlikedQuote = Quotes(
                quotes.id,
                quotes.body,
                quotes.authorName,
                quotes.tag,
                true
            )

            Toast.makeText(context, "Quote Liked", Toast.LENGTH_SHORT).show()
            mViewModel.updateLikeQuote(unlikedQuote)
        }
    }

    private fun navigateToQuotes(selectedTagTitle: String?, isLikedQuotes: Boolean = false) {
        val action = HomeFragmentDirections.actionHomeFragmentToQuotesFragment(
            selectedTagTitle,
            isLikedQuotes
        )
        navController?.navigate(action)
    }

    private fun navigateToEdit(quotes: Quotes?) {
        val action = HomeFragmentDirections.actionHomeFragmentToEditorFragment(quotes)
        navController?.navigate(action)
    }

    private fun handleLoading() {
        // TODO will set loading view later
    }

    private fun handleQuotesData(quotes: List<Quotes>) {
        with(fragmentBinding.quotesRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = QuotesRecyclerAdapter(
                quotes,
                ::navigateToEdit,
                ::shareQuote,
                ::likeQuote,
                ::deleteQuote
            )
        }
    }

    private fun handleTagData(tags: List<Tag>) {
        with(fragmentBinding.tagRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TagRecyclerAdapter(tags, ::navigateToQuotes)
        }
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
