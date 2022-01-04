package com.web0zz.wallquotes.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TAG_TABLE")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
)
