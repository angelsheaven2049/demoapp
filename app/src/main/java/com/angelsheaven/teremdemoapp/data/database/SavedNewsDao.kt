package com.angelsheaven.teremdemoapp.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedNewsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: SavedNews?): Long

    @Query("SELECT * FROM saved_news WHERE id = :id")
    fun getItem(id:Int):SavedNews

    @Query("DELETE FROM saved_news WHERE id = :id")
    fun deleteItem(id:Int):Int

    @Query("SELECT * FROM saved_news")
    fun getSavedNews(): DataSource.Factory<Int, News>?

}