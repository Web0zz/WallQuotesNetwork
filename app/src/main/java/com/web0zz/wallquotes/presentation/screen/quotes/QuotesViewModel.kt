package com.web0zz.wallquotes.presentation.screen.quotes

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.usecase.quotes.GetByTagUseCase
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
class QuotesViewModel @Inject constructor(
    private val getByTagUseCase: GetByTagUseCase
) : BaseViewModel() {

    private var _quotesUiState: MutableStateFlow<QuotesUiState> = MutableStateFlow(QuotesUiState.Loading)
    val quotesUiState: StateFlow<QuotesUiState> = _quotesUiState

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

    private fun setLoading() {
        _quotesUiState.value = QuotesUiState.Loading
    }

    private fun handleQuotesList(quotesData: List<Quotes>) {
        _quotesUiState.value = QuotesUiState.Success(quotesData)
    }

    private fun handleFailure(failure: Failure) {
        _quotesUiState.value = QuotesUiState.Error(failure)
    }

    sealed class QuotesUiState {
        object Loading : QuotesUiState()
        data class Success(val data: List<Quotes>) : QuotesUiState()
        data class Error(val failure: Failure) : QuotesUiState()
    }
}