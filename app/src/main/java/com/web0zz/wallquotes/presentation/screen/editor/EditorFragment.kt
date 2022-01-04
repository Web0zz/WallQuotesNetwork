package com.web0zz.wallquotes.presentation.screen.editor

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.FragmentEditorBinding
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@AndroidEntryPoint
class EditorFragment : BaseFragment<FragmentEditorBinding, EditorViewModel>(
    FragmentEditorBinding::inflate
) {
    override val mViewModel: EditorViewModel by viewModels()
    private val navController by lazy {
        activity?.let {
            Navigation.findNavController(it, R.id.nav_host_fragmentContainerView)
        }
    }

    private val safeArgs: EditorFragmentArgs by navArgs()

    private var isUpdate = false
    private lateinit var updateQuote: Quotes

    override fun onCreateInvoke() {
        val data = safeArgs.editQuotes
        if (data != null) {
            isUpdate = true
            updateQuote = data
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.editorUiState.collect { handleViewState(it) }
            }
        }
    }

    override fun onCreateViewInvoke() {
        if (isUpdate) initWithQuote() else initWithoutQuote()

        onTyping()

        fragmentBinding.editPublishImageButton.setOnClickListener { onPublishQuote() }
        fragmentBinding.editShareImageButton.setOnClickListener { onShareQuote() }
    }

    private fun initWithQuote() {
        fragmentBinding.editQuoteTextView.text = updateQuote.body
        fragmentBinding.editAuthorTextView.text = updateQuote.authorName
    }

    private fun initWithoutQuote() {
        fragmentBinding.editQuoteTextView.text = ""
        fragmentBinding.editAuthorTextView.text = getString(R.string.itsMe)
    }

    private fun handleViewState(viewState: EditorViewModel.EditorUiState) {
        when (viewState) {
            is EditorViewModel.EditorUiState.Loading -> handleLoading()
            is EditorViewModel.EditorUiState.Success -> handleEditorState(viewState.isDone)
            is EditorViewModel.EditorUiState.Error -> handleFailure(viewState.failure)
        }
    }

    private fun onTyping() {
        fragmentBinding.editTextTextInputEditText.addTextChangedListener {
            fragmentBinding.editQuoteTextView.text = it.toString()
        }
    }

    private fun onShareQuote() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, fragmentBinding.editQuoteTextView.text.toString())
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun onPublishQuote() {
        when (isUpdate) {
            true -> {
                val newBodyText = fragmentBinding.editQuoteTextView.text.toString()
                val quoteData = Quotes(
                    updateQuote.id,
                    if (newBodyText.isBlank()) updateQuote.body else newBodyText,
                    authorName = getString(R.string.itsMe),
                    tag = "your",
                    isLiked = updateQuote.isLiked
                )

                mViewModel.updateQuotes(quoteData)
            }
            false -> {
                val quoteData = Quotes(
                    body = fragmentBinding.editQuoteTextView.text.toString(),
                    authorName = getString(R.string.itsMe),
                    tag = "your",
                    isLiked = true
                )

                mViewModel.insertQuotes(quoteData)
            }
        }

        navController?.popBackStack()
    }

    private fun handleLoading() {
        // TODO will set loading view later
    }

    private fun handleEditorState(isDone: Boolean) {
        if (isDone) {
            Toast.makeText(context, "Quote Ready", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(context, "Quote Not Ready", Toast.LENGTH_SHORT).show()
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.UnknownError -> showFailureText(failure.message, failure.exceptionMessage)
        }
    }

    private fun showFailureText(message: String, exceptionMessage: String?) {
        Log.e("ERROR", "Error on Login: $exceptionMessage")
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}
