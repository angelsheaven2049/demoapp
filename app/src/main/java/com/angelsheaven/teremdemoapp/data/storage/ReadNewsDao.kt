package com.angelsheaven.teremdemoapp.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.angelsheaven.teremdemoapp.data.ReadNews

@Dao
interface ReadNewsDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: ReadNews?):Long

    @Query("SELECT * FROM read_news WHERE id = :id")
    fun getItem(id:Int): ReadNews

}