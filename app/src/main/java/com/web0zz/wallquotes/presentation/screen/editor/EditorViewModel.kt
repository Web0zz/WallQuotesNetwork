package com.web0zz.wallquotes.presentation.screen.editor

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.usecase.UseCase
import com.web0zz.wallquotes.domain.usecase.quotes.InsertQuotesUseCase
import com.web0zz.wallquotes.domain.usecase.quotes.UpdateQuotesUseCase
import com.web0zz.wallquotes.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class EditorViewModel @Inject constructor(
    private val insertQuotesUseCase: InsertQuotesUseCase,
    private val updateQuotesUseCase: UpdateQuotesUseCase,
) : BaseViewModel() {

    private val _editorUiState: MutableStateFlow<EditorUiState> =
        MutableStateFlow(EditorUiState.Loading)
    val editorUiState: StateFlow<EditorUiState> = _editorUiState

    fun insertQuotes(quotes: Quotes) {
        job?.cancel()

        insertQuotesUseCase(quotes, viewModelScope) {
            job = viewModelScope.launch {
                it.onStart { setLoading() }
                    .collect { result ->
                        result.mapBoth(::handleActionTrue, ::handleFailure)
                    }
            }
        }
    }

    fun updateQuotes(quotes: Quotes) {
        job?.cancel()

        updateQuotesUseCase(quotes, viewModelScope) {
            job = viewModelScope.launch {
                it.onStart { setLoading() }
                    .collect { result ->
                        result.mapBoth(::handleActionTrue, ::handleFailure)
                    }
            }
        }
    }

    private fun setLoading() {
        _editorUiState.value = EditorUiState.Loading
    }

    private fun handleActionTrue(isDone: UseCase.None) {
        _editorUiState.value = EditorUiState.Success(true)
    }

    private fun handleFailure(failure: Failure) {
        _editorUiState.value = EditorUiState.Error(failure)
    }

    sealed class EditorUiState {
        object Loading : EditorUiState()
        data class Success(val isDone: Boolean) : EditorUiState()
        data class Error(val failure: Failure) : EditorUiState()
    }
}
