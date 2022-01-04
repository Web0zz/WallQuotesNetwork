package com.web0zz.wallquotes.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quotes(
    val id: Int = 0,
    val body: String,
    val authorName: String,
    val tag: String,
    var isLiked: Boolean = false,
) : Parcelable
