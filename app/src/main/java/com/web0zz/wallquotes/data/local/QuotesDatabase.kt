package com.web0zz.wallquotes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.web0zz.wallquotes.data.local.model.QuotesEntity

@Database(entities = [QuotesEntity::class], version = 1)
abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quotesDao(): QuotesDao

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
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}