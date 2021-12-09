package com.web0zz.wallquotes.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Tag
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.usecase.*
import com.web0zz.wallquotes.domain.usecase.tag.GetAllTagUseCase
import com.web0zz.wallquotes.domain.usecase.quotes.GetQuotesUseCase
import com.web0zz.wallquotes.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getQuotesUseCase: GetQuotesUseCase,
    private val getAllTagUseCase: GetAllTagUseCase
) : BaseViewModel() {

    private val _homeQuotesUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val homeQuotesUiState: StateFlow<HomeUiState> = _homeQuotesUiState

    private val _homeTagUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val homeTagUiState: StateFlow<HomeUiState> = _homeTagUiState

    fun getAllQuotes() {
        job?.cancel()

        getQuotesUseCase(UseCase.None(), viewModelScope) {
            job = viewModelScope.launch {
                it.onStart { setLoading(true) }
                    .collect { result ->
                        result.mapBoth(::handleQuotesList) {
                            handleFailure(it, true)
                        }
                    }
            }
        }
    }

    fun getAllCategory() {
        job?.cancel()

        getAllTagUseCase(UseCase.None(), viewModelScope) {
            job = viewModelScope.launch {
                it.onStart { setLoading(false) }
                    .collect { result ->
                        result.mapBoth(::handleCategoryList) {
                            handleFailure(it, false)
                        }
                    }
            }
        }
    }

    private fun setLoading(isQuotes: Boolean) {
        if (isQuotes) _homeQuotesUiState.value = HomeUiState.Loading
        else _homeTagUiState.value = HomeUiState.Loading
    }

    private fun handleQuotesList(quotesData: List<Quotes>) {
        _homeQuotesUiState.value = HomeUiState.Success(quotesData, null)
    }

    private fun handleCategoryList(categoriesData: List<Tag>) {
        _homeTagUiState.value = HomeUiState.Success(null, categoriesData)
    }

    private fun handleFailure(failure: Failure, isQuotes: Boolean) {
        if (isQuotes) _homeQuotesUiState.value = HomeUiState.Error(failure)
        else _homeTagUiState.value = HomeUiState.Error(failure)
    }

    sealed class HomeUiState {
        object Loading : HomeUiState()
        data class Success(val quotesData: List<Quotes>?, val tagData: List<Tag>?) : HomeUiState()
        data class Error(val failure: Failure) : HomeUiState()
    }
}