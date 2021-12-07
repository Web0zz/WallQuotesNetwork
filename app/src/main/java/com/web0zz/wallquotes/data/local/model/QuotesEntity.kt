package com.web0zz.wallquotes.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QUOTES_TABLE")
data class QuotesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String,
    val publishTime: String,
    val username: String,
    val userProfileImageUrl: String
)