package com.web0zz.wallquotes.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.mapBoth
import com.web0zz.wallquotes.domain.exception.Failure
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.usecase.*
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
    private val getQuotesUseCase: GetQuotesUseCase
) : BaseViewModel() {

    private val _homeUiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val homeUiState: StateFlow<HomeUiState> = _homeUiState


    fun loadLaunches() {
        job?.cancel()

        getQuotesUseCase(UseCase.None(), viewModelScope) {
            job = viewModelScope.launch {
                it.onStart { setLoading() }
                    .collect { result ->
                        result.mapBoth(::handleQuotesList, ::handleFailure)
                    }
            }
        }
    }

    private fun setLoading() {
        _homeUiState.value = HomeUiState.Loading
    }

    private fun handleQuotesList(quotesData: List<Quotes>) {
        _homeUiState.value = HomeUiState.Success(quotesData)
    }

    private fun handleFailure(failure: Failure) {
        _homeUiState.value = HomeUiState.Error(failure)
    }

    sealed class HomeUiState {
        object Loading : HomeUiState()
        data class Success(val data: List<Quotes>) : HomeUiState()
        data class Error(val failure: Failure) : HomeUiState()
    }
}