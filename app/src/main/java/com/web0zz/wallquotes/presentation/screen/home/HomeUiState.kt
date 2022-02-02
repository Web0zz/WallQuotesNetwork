package com.web0zz.wallquotes.presentation.screen.home

import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.model.Tag

data class HomeUiState(
    val tags: List<Tag> = listOf(),
    val quotes: List<Quotes> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
