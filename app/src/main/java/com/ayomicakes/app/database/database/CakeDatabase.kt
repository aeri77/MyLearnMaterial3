package com.ayomicakes.app.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ayomicakes.app.database.dao.CakeDao
import com.ayomicakes.app.database.model.CakeItem
import dagger.Binds

@Database(entities = [CakeItem::class], version = 1)
abstract class CakeDatabase : RoomDatabase() {
    abstract fun cakeDao(): CakeDao

    companion object {
        fun getDatabase(context: Context): CakeDatabase {
            return Room.databaseBuilder(context, CakeDatabase::class.java, "cake_database")
                .fallbackToDestructiveMigration()
                .build()

        }
    }
}
