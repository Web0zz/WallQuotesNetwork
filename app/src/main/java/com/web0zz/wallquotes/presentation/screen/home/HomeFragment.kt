package com.web0zz.wallquotes.presentation.screen.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.FragmentHomeBinding
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.model.Tag
import com.web0zz.wallquotes.presentation.MainActivity
import com.web0zz.wallquotes.presentation.adapter.home.QuotesRecyclerAdapter
import com.web0zz.wallquotes.presentation.adapter.home.TagRecyclerAdapter
import com.web0zz.wallquotes.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val navController by lazy {
        activity?.let {
            Navigation.findNavController(it, R.id.nav_host_fragmentContainerView)
        }
    }

    private val mViewModel: HomeViewModel by viewModels()
    override val progressBar: View by lazy {
        (requireActivity() as MainActivity).progressBar
    }

    private var quotesRecyclerAdapter: QuotesRecyclerAdapter? = null
    private var quotesRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()
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
                    mViewModel.homeUiState.collect { handleHomeUiState(it) }
                }
            }
        }

        fragmentBinding.homeAddQuoteFloatingActionButton.setOnClickListener {
            navigateToEdit(null)
        }
    }

    private fun handleHomeUiState(uiState: HomeUiState) {
        setProgressStatus(uiState.isLoading)

        uiState.errorMessage?.let {
            showErrorDialog("Error on Home Screen", it)
        }

        handleQuotesData(uiState.quotes)
        handleTagData(uiState.tags)
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

    private fun initRecyclerViewItems(quotesList: List<Quotes>) {
        if (quotesRecyclerView == null || quotesRecyclerAdapter == null) {
            initRecyclerView(quotesList)
        } else if (fragmentBinding.quotesRecyclerView.adapter == null) {
            with(fragmentBinding.quotesRecyclerView) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = quotesRecyclerAdapter
            }

            quotesRecyclerAdapter!!.differ.submitList(quotesList)
        } else {
            quotesRecyclerAdapter!!.differ.submitList(quotesList)
        }
    }

    private fun initRecyclerView(quotesList: List<Quotes>) {
        with(fragmentBinding.quotesRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = quotesRecyclerAdapter ?: QuotesRecyclerAdapter(
                ::navigateToEdit,
                ::shareQuote,
                ::likeQuote,
                ::deleteQuote
            ).also {
                quotesRecyclerAdapter = it
            }

            quotesRecyclerView = quotesRecyclerView ?: this
        }

        quotesRecyclerAdapter!!.differ.submitList(quotesList)
    }

    private fun handleQuotesData(quotes: List<Quotes>) {
        initRecyclerViewItems(quotes)
    }

    private fun handleTagData(tags: List<Tag>) {
        with(fragmentBinding.tagRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = TagRecyclerAdapter(tags, ::navigateToQuotes)
        }
    }
}
