package com.angelsheaven.teremdemoapp.data.storage

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.angelsheaven.teremdemoapp.data.News
import com.angelsheaven.teremdemoapp.data.SavedNews

@Dao
interface SavedNewsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: SavedNews?): Long

    @Query("SELECT * FROM saved_news WHERE id = :id")
    fun getItem(id:Int): SavedNews

    @Query("DELETE FROM saved_news WHERE id = :id")
    fun deleteItem(id:Int):Int

    @Query("SELECT * FROM saved_news")
    fun getSavedNews(): DataSource.Factory<Int, News>?

}