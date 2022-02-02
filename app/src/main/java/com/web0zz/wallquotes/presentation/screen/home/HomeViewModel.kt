package com.web0zz.wallquotes.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.model.Tag
import com.web0zz.wallquotes.domain.usecase.UseCase
import com.web0zz.wallquotes.domain.usecase.quotes.DeleteQuotesUseCase
import com.web0zz.wallquotes.domain.usecase.quotes.GetQuotesUseCase
import com.web0zz.wallquotes.domain.usecase.quotes.UpdateQuotesUseCase
import com.web0zz.wallquotes.domain.usecase.tag.GetAllTagUseCase
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
class HomeViewModel @Inject constructor(
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getAllTagUseCase: GetAllTagUseCase,
    private val deleteQuotesUseCase: DeleteQuotesUseCase,
    private val updateQuotesUseCase: UpdateQuotesUseCase,
) : BaseViewModel() {

    private val _homeUiState: MutableStateFlow<HomeUiState> =
        MutableStateFlow(HomeUiState(isLoading = true))
    val homeUiState: StateFlow<HomeUiState> get() = _homeUiState

    fun getAllQuotes() {
        job?.cancel()

        getQuotesUseCase(UseCase.None(), viewModelScope) {
            job = viewModelScope.launch {
                it.onStart { setLoading() }
                    .collect { result ->
                        result.mapBoth(::handleQuotesList) {
                            handleFailure(it)
                        }
                    }
            }
        }
    }

    fun getAllTag() {
        job?.cancel()

        getAllTagUseCase(UseCase.None(), viewModelScope) {
            job = viewModelScope.launch {
                it.onStart { setLoading() }
                    .collect { result ->
                        result.mapBoth(::handleTagList) {
                            handleFailure(it)
                        }
                    }
            }
        }
    }

    fun deleteQuote(quotes: Quotes) {
        job?.cancel()

        deleteQuotesUseCase(quotes, viewModelScope) {
            job = viewModelScope.launch {
                it.collect { getAllQuotes() }
            }
        }
    }

    fun updateLikeQuote(quotes: Quotes) {
        job?.cancel()

        updateQuotesUseCase(quotes, viewModelScope) {
            job = viewModelScope.launch {
                it.collect { getAllQuotes() }
            }
        }
    }

    private fun setLoading() {
        _homeUiState.update { currentUiState ->
            currentUiState.copy(isLoading = true)
        }
    }

    private fun handleQuotesList(quotesData: List<Quotes>) {
        _homeUiState.update { currentUiState ->
            currentUiState.copy(quotes = quotesData)
        }
    }

    private fun handleTagList(tagData: List<Tag>) {
        _homeUiState.update { currentUiState ->
            currentUiState.copy(tags = tagData)
        }
    }

    private fun handleFailure(failure: Failure) {
        _homeUiState.update { currentUiState ->
            currentUiState.copy(errorMessage = failure.message)
        }
    }
}
