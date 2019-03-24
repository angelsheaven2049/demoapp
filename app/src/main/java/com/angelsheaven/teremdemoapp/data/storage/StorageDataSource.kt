package com.angelsheaven.teremdemoapp.data.storage

import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.angelsheaven.teremdemoapp.FILTER_BY_ALL
import com.angelsheaven.teremdemoapp.SORT_BY_NONE
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import io.reactivex.Flowable
import javax.inject.Inject

class StorageDataSource @Inject constructor(
    private val mDatabase: AppDatabase?) : MyLogger {

    /*companion object {
        private var sInstance: StorageDataSource? = null
        *//**
         * get the singleton for this class
         *//*
        fun getInstance(mDatabase: AppDatabase?): StorageDataSource? {
            return sInstance ?: synchronized(this) {
                StorageDataSource(
                    mDatabase
                ).also {
                    sInstance = it
                }
            }
        }

    }*/

    fun saveNewsDetail(news: News?): Long? {
        return mDatabase?.newsDao()?.insert(news)
    }

    fun getReadNews(newsId: Int): ReadNews? {
        return mDatabase?.readNewsDao()?.getItem(newsId)
    }

    //Delete all existing news data in local database
    fun resetNewsData(): Int? {
        return mDatabase?.newsDao()?.deleteOldItems()
    }

    fun retrieveNewsDetail(mNewsId: Int): Flowable<News>? {
        val queryNewsDetail = SELECT_NEWS_DETAIL.format(mNewsId)
        return mDatabase?.newsDao()
            ?.getNewsDetail(SimpleSQLiteQuery(queryNewsDetail))
    }

    fun deleteNewsItem(newsId: Int): Int? {
        return mDatabase?.newsDao()?.deleteItem(newsId)
    }

    fun storeReadsNews(readNews: ReadNews?): Long? {
        return mDatabase?.readNewsDao()?.insert(readNews)
    }

    fun updateNewsFieldSaved(newsId: Int, isSaved: Boolean): Int? {
        val convertSavedValueToSqlBoolean = if (isSaved) 1 else 0
        val updateSpecificNewsSavedFieldSqlStatement =
            UPDATE_SPECIFIC_NEWS_SAVED_FIELD.format(convertSavedValueToSqlBoolean, newsId)
        return mDatabase?.newsDao()?.updateSavedFieldV2(SimpleSQLiteQuery(updateSpecificNewsSavedFieldSqlStatement))
    }

    fun queryNews(
        filterOption: String,
        sortOption: String,
        userQuery: String
    ): DataSource.Factory<Int, News>? {

        val sqlConditionKeyword = " WHERE"
        val sqlAndOperator = " and "

        var queryString = "SELECT * FROM $TABLE_NEWS "

        if (filterOption.isNotEmpty() && filterOption != FILTER_BY_ALL) {
            if (!queryString.contains(sqlConditionKeyword)) {
                queryString += sqlConditionKeyword
            }
            queryString += " type = '$filterOption' "
        }

        if (userQuery.isNotEmpty()) {
            queryString += if (!queryString.contains(sqlConditionKeyword)) {
                sqlConditionKeyword
            } else {
                sqlAndOperator
            }
            queryString += " (title LIKE '%$userQuery%' " +
                    "or 'by' LIKE '%$userQuery%') "
        }

        if (sortOption.isNotEmpty() && sortOption != SORT_BY_NONE) {
            queryString += "ORDER BY $sortOption DESC "
        }

        return mDatabase?.newsDao()?.getAllNews(SimpleSQLiteQuery(queryString))
    }

    suspend fun insertNewsToSavedNewsTable(news: SavedNews?): Long? {
        return mDatabase?.savedNewsDao()?.insert(news)
    }

    fun deleteSavedNewsInSavedNewsTable(newsId: Int): Int? {
        return mDatabase?.savedNewsDao()?.deleteItem(newsId)
    }

    fun getAllSavedNews(): DataSource.Factory<Int, News>? {
        return mDatabase?.savedNewsDao()?.getSavedNews()
    }


}
