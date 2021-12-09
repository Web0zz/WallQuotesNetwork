package com.web0zz.wallquotes.data.repository.mapper

import com.web0zz.wallquotes.data.local.model.TagEntity
import com.web0zz.wallquotes.domain.model.Tag

fun mapTagEntity(input: TagEntity): Tag {
    return Tag(
        id = input.id,
        title = input.title
    )
}