package com.angelsheaven.teremdemoapp.data.storage

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import io.reactivex.Flowable

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: News?): Long

    @RawQuery(observedEntities = [News::class])
    fun getAllNews(rawQuery: SupportSQLiteQuery): DataSource.Factory<Int, News>

    @RawQuery(observedEntities = [News::class])
    fun getNewsDetail(rawQuery: SupportSQLiteQuery): Flowable<News>

    @Query(DELETE_ALL_NEWS)
    fun deleteOldItems(): Int

    @Query("$DELETE_SPECIFIC_NEWS :id")
    fun deleteItem(id: Int): Int

    @Query("UPDATE news SET saved = :saved WHERE id = :id")
    fun updateSavedField(id: Int, saved: Boolean)

    @Query("UPDATE news SET saved = :saved WHERE id = :id")
    fun updateSavedFieldV2(id: Int, saved: Boolean): Int


}