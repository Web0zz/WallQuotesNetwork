package com.web0zz.wallquotes.presentation.screen.quotes

import com.web0zz.wallquotes.domain.model.Quotes

data class QuotesUiState(
    val quoteList: List<Quotes> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
