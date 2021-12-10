package com.web0zz.wallquotes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.web0zz.wallquotes.data.local.model.QuotesEntity
import com.web0zz.wallquotes.data.local.model.TagEntity

@Database(entities = [QuotesEntity::class, TagEntity::class], version = 1)
abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quotesDao(): QuotesDao
    abstract fun tagDao(): TagDao

    companion object {
        private const val DB_NAME = "quotes_db"

        @Volatile
        private var INSTANCE: QuotesDatabase? = null

        fun getInstance(context: Context): QuotesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    QuotesDatabase::class.java,
                    DB_NAME
                ).createFromAsset("database/pre_quotes.db").build()

                INSTANCE = instance
                return instance
            }
        }
    }
}