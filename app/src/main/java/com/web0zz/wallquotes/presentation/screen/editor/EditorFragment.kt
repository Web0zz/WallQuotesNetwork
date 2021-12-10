package com.web0zz.wallquotes.presentation.screen.editor

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.web0zz.wallquotes.R
import com.web0zz.wallquotes.databinding.FragmentEditorBinding
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.presentation.base.BaseFragment
import com.web0zz.wallquotes.presentation.util.FragmentUtil.getFragmentNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@DelicateCoroutinesApi
@AndroidEntryPoint
class EditorFragment : BaseFragment<FragmentEditorBinding, EditorViewModel>(
    FragmentEditorBinding::inflate
) {
    override val mViewModel: EditorViewModel by viewModels()
    private val safeArgs: EditorFragmentArgs by navArgs()
    private val navController by lazy {
        getFragmentNavController(R.id.nav_host_fragmentContainerView)
    }

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
        fragmentBinding.editWriterTextView.text = updateQuote.authorName
    }

    private fun initWithoutQuote() {
        fragmentBinding.editQuoteTextView.text = ""
        fragmentBinding.editWriterTextView.text = getString(R.string.itsMe)
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
        when(isUpdate) {
            true -> {
                val newBodyText = fragmentBinding.editQuoteTextView.text.toString()
                val quoteData = Quotes(
                    updateQuote.id,
                    if (newBodyText.isBlank()) updateQuote.body else newBodyText,
                    authorName = getString(R.string.itsMe),
                    tag = "your",
                    isLiked = updateQuote.isLiked
                )

                Toast.makeText(context, "Quote data updated", Toast.LENGTH_SHORT).show()
                mViewModel.updateQuotes(quoteData)
            }
            false -> {
                val quoteData = Quotes(
                    body = fragmentBinding.editQuoteTextView.text.toString(),
                    authorName = getString(R.string.itsMe),
                    tag = "your",
                    isLiked = true
                )

                Toast.makeText(context, "New Quote data inserted", Toast.LENGTH_SHORT).show()
                mViewModel.insertQuotes(quoteData)
            }
        }
    }

    // Handle HomeUiState

    private fun handleLoading() {

    }

    private fun handleEditorState(isDone: Boolean) {
        if (isDone) Toast.makeText(context, "Quote Ready", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Quote Not Ready", Toast.LENGTH_SHORT).show()
    }

    private fun handleFailure(failure: Failure) {
        when (failure) {
            is Failure.UnknownError -> showFailureText(failure.message, failure.exceptionMessage)
        }
    }

    private fun showFailureText(message: String, exceptionMessage: String?) {
        Log.e("ERROR","Error on Login: $exceptionMessage")
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}