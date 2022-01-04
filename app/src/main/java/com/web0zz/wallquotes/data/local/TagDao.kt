package com.web0zz.wallquotes.data.local

import androidx.room.Dao
import androidx.room.Query
import com.web0zz.wallquotes.data.local.model.TagEntity

@Dao
interface TagDao {
    @Query("SELECT * FROM tag_table")
    fun getAllTags(): List<TagEntity>
}
