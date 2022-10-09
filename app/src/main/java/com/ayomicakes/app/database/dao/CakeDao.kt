package com.ayomicakes.app.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ayomicakes.app.database.model.CakeItem

@Dao
interface CakeDao {
    @Query("SELECT * FROM cakes")
    fun getAll(): List<CakeItem>

    @Query("SELECT * FROM cakes WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<CakeItem>

    @Query("SELECT * FROM cakes WHERE cakeName LIKE :name LIMIT 1")
    fun findByName(name: String): CakeItem

    @Insert
    fun insertAll(vararg cakes: CakeItem)

    @Delete
    fun delete(cake: CakeItem)
}