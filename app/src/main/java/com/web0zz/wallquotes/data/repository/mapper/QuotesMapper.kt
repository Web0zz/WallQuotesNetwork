package com.web0zz.wallquotes.data.repository.mapper

import com.web0zz.wallquotes.data.local.model.QuotesEntity
import com.web0zz.wallquotes.domain.model.Quotes

fun mapQuotesToEntity(quotes: Quotes): QuotesEntity {
    return QuotesEntity(
        id = quotes.id,
        text = quotes.text,
        publishTime = quotes.publishTime
    )
}