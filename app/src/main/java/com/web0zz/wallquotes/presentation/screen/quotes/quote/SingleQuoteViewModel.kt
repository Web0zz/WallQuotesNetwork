package com.web0zz.wallquotes.presentation.screen.quotes.quote

import androidx.lifecycle.viewModelScope
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.usecase.quotes.DeleteQuotesUseCase
import com.web0zz.wallquotes.domain.usecase.quotes.UpdateQuotesUseCase
import com.web0zz.wallquotes.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class SingleQuoteViewModel @Inject constructor(
    private val updateQuotesUseCase: UpdateQuotesUseCase,
    private val deleteQuotesUseCase: DeleteQuotesUseCase
) : BaseViewModel(){
    // TODO add event result listener soon

    fun updateQuote(quote: Quotes) {
        updateQuotesUseCase(quote, viewModelScope) {}
    }

    fun deleteQuote(quote: Quotes) {
        deleteQuotesUseCase(quote, viewModelScope) {}
    }
}