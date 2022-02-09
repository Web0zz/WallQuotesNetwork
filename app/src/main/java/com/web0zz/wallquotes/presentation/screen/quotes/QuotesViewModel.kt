package com.web0zz.wallquotes.presentation.screen.quotes

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.usecase.UseCase
import com.web0zz.wallquotes.domain.usecase.quotes.GetByTagUseCase
import com.web0zz.wallquotes.domain.usecase.quotes.GetLikedQuotesUseCase
import com.web0zz.wallquotes.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class QuotesViewModel @Inject constructor(
    private val getByTagUseCase: GetByTagUseCase,
    private val getLikedQuotesUseCase: GetLikedQuotesUseCase,
) : BaseViewModel() {

    private var _quotesUiState: MutableStateFlow<QuotesUiState> =
        MutableStateFlow(QuotesUiState(isLoading = true))
    val quotesUiState: StateFlow<QuotesUiState> get() = _quotesUiState

    fun getByTag(selectedTag: String) {
        job?.cancel()

        getByTagUseCase(selectedTag, viewModelScope) {
            job = viewModelScope.launch {
                it.onStart { setLoading() }
                    .collect { result ->
                        result.mapBoth(::handleQuotesList, ::handleFailure)
                    }
            }
        }
    }

    fun getLikedQuotes() {
        job?.cancel()

        getLikedQuotesUseCase(UseCase.None(), viewModelScope) {
            job = viewModelScope.launch {
                it.onStart { setLoading() }
                    .collect { result ->
                        result.mapBoth(::handleQuotesList, ::handleFailure)
                    }
            }
        }
    }

    private fun setLoading() {
        _quotesUiState.update { currentUiState ->
            currentUiState.copy(isLoading = true)
        }
    }

    private fun handleQuotesList(quotesData: List<Quotes>) {
        _quotesUiState.update { currentUiState ->
            currentUiState.copy(isLoading = false, quoteList = quotesData)
        }
    }

    private fun handleFailure(failure: Failure) {
        _quotesUiState.update { currentUiState ->
            currentUiState.copy(isLoading = false, errorMessage = failure.message)
        }
    }
}
