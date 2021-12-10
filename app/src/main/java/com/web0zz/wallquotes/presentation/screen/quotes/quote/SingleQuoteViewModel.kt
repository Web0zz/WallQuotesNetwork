package com.web0zz.wallquotes.presentation.screen.quotes.quote

import androidx.lifecycle.viewModelScope
import com.web0zz.wallquotes.domain.model.Quotes
import com.web0zz.wallquotes.domain.usecase.quotes.DeleteQuotesUseCase
import com.web0zz.wallquotes.domain.usecase.quotes.UpdateQuotesUseCase
import com.web0zz.wallquotes.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@DelicateCoroutinesApi
@HiltViewModel
class SingleQuoteViewModel @Inject constructor(
    private val updateQuotesUseCase: UpdateQuotesUseCase
) : BaseViewModel(){
    // TODO add event result listener soon

    fun updateQuote(quote: Quotes) {
        job?.cancel()

        updateQuotesUseCase(quote, viewModelScope) {
            job = viewModelScope.launch {
                it.collect { }
            }
        }
    }
}