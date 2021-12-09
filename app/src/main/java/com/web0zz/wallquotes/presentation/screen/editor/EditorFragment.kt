package com.web0zz.wallquotes.presentation.screen.editor

import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.web0zz.wallquotes.databinding.FragmentEditorBinding
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.presentation.base.BaseFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EditorFragment : BaseFragment<FragmentEditorBinding, EditorViewModel>(
    FragmentEditorBinding::inflate
) {
    override val mViewModel: EditorViewModel by viewModels()

    override fun onCreateInvoke() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.editorUiState.collect { handleViewState(it) }
            }
        }
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

    // Handle HomeUiState

    private fun handleLoading() {
        // TODO set loading state
    }

    private fun handleEditorState(isDone: Boolean) {
        // TODO set launches data to xml
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