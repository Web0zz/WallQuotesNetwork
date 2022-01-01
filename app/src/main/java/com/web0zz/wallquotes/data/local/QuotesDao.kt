package com.web0zz.wallquotes.data.local

import androidx.room.*
import com.web0zz.wallquotes.data.local.model.QuotesEntity

@Dao
interface QuotesDao {
    @Query("SELECT * FROM quotes_table ORDER BY id DESC")
    fun getAllQuotes(): List<QuotesEntity>

    @Query("SELECT * FROM quotes_table WHERE tag = :selectedTag")
    fun getByTag(selectedTag: String): List<QuotesEntity>

    @Query("SELECT * FROM quotes_table WHERE isLiked = 1")
    fun getLikedQuotes(): List<QuotesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuotes(quotes: QuotesEntity)

    @Delete
    fun deleteQuotes(quotes: QuotesEntity)

    @Update
    fun updateQuotes(quotes: QuotesEntity)
}