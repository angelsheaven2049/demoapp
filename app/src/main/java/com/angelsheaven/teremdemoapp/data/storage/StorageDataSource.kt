package com.angelsheaven.teremdemoapp.data.storage

import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.angelsheaven.teremdemoapp.data.News
import com.angelsheaven.teremdemoapp.data.ReadNews
import com.angelsheaven.teremdemoapp.data.SavedNews
import com.angelsheaven.teremdemoapp.utilities.FILTER_BY_ALL
import com.angelsheaven.teremdemoapp.utilities.SORT_BY_NONE
import io.reactivex.Flowable

class StorageDataSource(
    private val mDatabase: AppDatabase?) {

    companion object {
        private var sInstance: StorageDataSource? = null
        /**
         * get the singleton for this class
         */
        fun getInstance(mDatabase: AppDatabase?): StorageDataSource? {
            return sInstance ?: synchronized(this) {
                StorageDataSource(
                    mDatabase
                ).also {
                    sInstance = it
                }
            }
        }

    }

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
        return mDatabase?.newsDao()?.getNewsDetail(mNewsId)
    }

    fun deleteNewsItem(newsId: Int): Int? {
        return mDatabase?.newsDao()?.deleteItem(newsId)
    }

    fun storeReadsNews(readNews: ReadNews?): Long? {
        return mDatabase?.readNewsDao()?.insert(readNews)
    }

    fun updateNewsFieldSaved(newsId: Int, isSaved: Boolean): Int? {
        return mDatabase?.newsDao()?.updateSavedFieldV2(newsId, isSaved)
    }

    fun queryNews(
        filterOption: String,
        sortOption: String,
        userQuery: String
    ): DataSource.Factory<Int, News>? {

        val sqlConditionKeyword = " WHERE"
        val sqlAndOperator = " and "

        var queryString = "SELECT * FROM news "

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
