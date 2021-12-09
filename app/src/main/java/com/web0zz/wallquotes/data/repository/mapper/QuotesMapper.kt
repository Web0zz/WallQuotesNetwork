package com.web0zz.wallquotes.data.repository.mapper

import com.web0zz.wallquotes.data.local.model.QuotesEntity
import com.web0zz.wallquotes.domain.model.Quotes

fun mapQuotesToEntity(input: Quotes): QuotesEntity {
    return QuotesEntity(
        id = input.id,
        body = input.body,
        authorName = input.authorName,
        tag = input.tag
    )
}