package com.angelsheaven.teremdemoapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.angelsheaven.teremdemoapp.data.database.News
import com.angelsheaven.teremdemoapp.data.database.NewsSearchResult
import com.angelsheaven.teremdemoapp.data.database.ReadNews
import com.angelsheaven.teremdemoapp.data.database.SavedNews
import com.angelsheaven.teremdemoapp.data.network.NetworkDataSource
import com.angelsheaven.teremdemoapp.data.storage.StorageDataSource
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import com.angelsheaven.teremdemoapp.utilities.configLoadData
import io.reactivex.Flowable
import kotlinx.coroutines.*
import java.util.*


class Repository(
    private val networkDataSource: NetworkDataSource?
    , private val storageDataSource: StorageDataSource?
    , private val isDataInitialized: Boolean?
) : MyLogger {

    val networkConnectionState by lazy { MutableLiveData<Boolean>() }
    private val networkErrors by lazy { MutableLiveData<String>() }
    val updateInitializeData by lazy { MutableLiveData<Boolean>() }

    //For singleton instantiation
    companion object {
        private var sInstance: Repository? = null

        @Synchronized
        fun getInstance(
            networkDataSource: NetworkDataSource?
            , storageDataSource: StorageDataSource?
            , isItInitializedData: Boolean?
        ): Repository? {
            return sInstance ?: synchronized(this) {
                Repository(
                    networkDataSource,
                    storageDataSource,
                    isItInitializedData
                ).also {
                    sInstance = it
                }
            }
        }
    }

    suspend fun markNewsRead(news: News?): Boolean {

        return withContext(Dispatchers.IO) {
            news?.run {
                val saveReadNewsTask =
                    async { storageDataSource?.storeReadsNews(ReadNews(Date()).createReadNews(news)) }

                val resultSaveReadNewsTask = saveReadNewsTask.await()

                if (resultSaveReadNewsTask != null && resultSaveReadNewsTask > 0) {
                    val deleteNewsInNewsTableTask = async { storageDataSource?.deleteNewsItem(news.id) }

                    val resultDeleteNewsInNewsTableTask = deleteNewsInNewsTableTask.await()

                    return@withContext resultDeleteNewsInNewsTableTask != null && resultDeleteNewsInNewsTableTask > 0
                }
            }
            return@withContext true
        }

    }

    fun search(
        filterOption: String,
        sortOption: String,
        mUserQuery: String,
        onComplete: (Boolean) -> Unit
    ): NewsSearchResult? {

        getAndSaveData()

        val queryData =
            storageDataSource?.queryNews(filterOption, sortOption, mUserQuery)

        var formatPagingData: LiveData<PagedList<News>>? = null

        queryData?.run {
            formatPagingData = LivePagedListBuilder(this, configLoadData)
                .setInitialLoadKey(null)
                .build()
        }

        onComplete(formatPagingData != null)

        return NewsSearchResult(formatPagingData, networkErrors)
    }

    @Synchronized
    private fun getAndSaveData() {

        log("Isinitialize $isDataInitialized")

        if (isDataInitialized == true) return

        GlobalScope.launch {
            retrieveNewNews()
        }
    }

    suspend fun updateNewsFieldSaved(news: News?, isSaved: Boolean): Boolean {
        news?.run {
            return if (isSaved) {
                turnANewsToSaved(news)
            } else {
                turnANewsToUnSaved(news)
            }
        }
        return false
    }

    private suspend fun turnANewsToUnSaved(news: News): Boolean {
        return withContext(Dispatchers.IO) {

            try {
                log("News id ${news.id}")
                val deleteANewsInSavedNewsTableTask = async {
                    storageDataSource
                        ?.deleteSavedNewsInSavedNewsTable(news.id)
                }

                val deleteSavedNewsTaskResult = deleteANewsInSavedNewsTableTask.await()

                log("delete saved news task result $deleteSavedNewsTaskResult")

                if (deleteSavedNewsTaskResult != null && deleteSavedNewsTaskResult >= 1) {
                    /*
                       if delete succeed then update saved field in table news turn true
                     */
                    val updateSavedFieldInTableNewsTrueTask = async {
                        storageDataSource?.updateNewsFieldSaved(news.id, false)
                    }

                    val resultUpdateSavedFieldInTableNewsTrueTask = updateSavedFieldInTableNewsTrueTask.await()

                    log("Update task result is $resultUpdateSavedFieldInTableNewsTrueTask")

                    return@withContext resultUpdateSavedFieldInTableNewsTrueTask != null
                            && resultUpdateSavedFieldInTableNewsTrueTask >= 1
                } else {
                    return@withContext false
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                return@withContext false
            }

        }
    }

    private suspend fun turnANewsToSaved(news: News): Boolean {
        return withContext(Dispatchers.IO) {

            try {
                val insertNewsToSavedNewsTableTask = async {
                    storageDataSource
                        ?.insertNewsToSavedNewsTable(SavedNews(Date()).createSavedNews(news))
                }

                val insertNewsSavedToTableTaskResult = insertNewsToSavedNewsTableTask.await()

                if (insertNewsSavedToTableTaskResult != null) {
                    /**
                     * If insert success then update saved value become to true
                     * in news table
                     */
                    val updateSavedValueTask = async {
                        storageDataSource?.updateNewsFieldSaved(news.id, true)
                    }

                    val updateSavedValueTaskResult = updateSavedValueTask.await()

                    log("Update task result is $updateSavedValueTaskResult")

                    return@withContext updateSavedValueTaskResult != null && updateSavedValueTaskResult >= 1
                } else {
                    return@withContext false
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                return@withContext false
            }
        }
    }

    fun getSavedNews(): NewsSearchResult? {

        val queryData = storageDataSource?.getAllSavedNews()

        var formatPagingData: LiveData<PagedList<News>>? = null

        queryData?.run {
            formatPagingData = LivePagedListBuilder(this, configLoadData)
                .setInitialLoadKey(null)
                .build()
        }

        return NewsSearchResult(formatPagingData, networkErrors)
    }

    suspend fun retrieveNewNews() {
        withContext(Dispatchers.IO) {
            try {
                val response = networkDataSource?.requestNews()

                response?.run {
                    if (response.isSuccessful) {
                        val newsIdList: MutableList<Int>? = response.body() as MutableList<Int>?
                        newsIdList?.run {
                            if (this.isNotEmpty()) {

                                val taskDeleteAllNews = async { storageDataSource?.resetNewsData() }

                                val resultDeleteAllNewsTask = taskDeleteAllNews.await()

                                if (resultDeleteAllNewsTask != null) {

                                    do {
                                        try {

                                            val newsID = newsIdList.first()

                                            val taskDownloadAndSavedNewsTask =
                                                async { downloadAndSaveNewsSingleNews(newsID) }

                                            val resultTaskDownloadAndSaveNews = taskDownloadAndSavedNewsTask.await()

                                            log("Result download news $newsID is $resultTaskDownloadAndSaveNews")

                                            if (resultTaskDownloadAndSaveNews)
                                                newsIdList.remove(newsID)
                                        } catch (ex: java.lang.Exception) {
                                            throw ex
                                        }
                                    } while (newsIdList.isNotEmpty())

                                }

                                updateInitializeData.postValue(true)
                            }
                        }

                    } else {
                        networkErrors.postValue("server has error ${response.code()}")
                        log("server has error ${response.code()}")
                    }
                }

            } catch (error: Exception) {
                networkErrors.postValue(error.message)
                error.printStackTrace()
            }
        }
    }

    private suspend fun downloadAndSaveNewsSingleNews(newsID: Int): Boolean {

        return withContext(Dispatchers.IO) {
            try {
                val detailResponse =
                    networkDataSource?.requestNewsDetail(newsID)

                detailResponse?.run {
                    if (this.isSuccessful) {
                        val singleNews = this.body()

                        log(singleNews.toString())

                        /**
                         * Check and save news
                         * if news exist in read news
                         * mark it as read news
                         */
                        singleNews?.run {

                            val saveNewsTask = async { storageDataSource?.saveNewsDetail(singleNews) }

                            val resultSaveNewsTask = saveNewsTask.await()

                            if (resultSaveNewsTask != null && resultSaveNewsTask > 0) {
                                val checkNewsExistInReadNewsTableTask =
                                    async { storageDataSource?.getReadNews(singleNews.id) }

                                val resultCheckNewsExistInReadNewsTableTask = checkNewsExistInReadNewsTableTask.await()

                                /**
                                 * if news exist in saved news table then
                                 * update saved field to true
                                 */
                                if (resultCheckNewsExistInReadNewsTableTask != null) {
                                    val updateSavedNewsTrueTask = async {
                                        storageDataSource
                                            ?.updateNewsFieldSaved(singleNews.id, true)
                                    }
                                    val resultUpdateSavedNewsTrueTask = updateSavedNewsTrueTask.await()
                                    return@withContext resultUpdateSavedNewsTrueTask != null
                                            && resultUpdateSavedNewsTrueTask > 0
                                }
                            }

                        }

                    } else {
                        log("Unable to download news $newsID - returned error ${detailResponse.code()}")
                    }
                }

                return@withContext true
            } catch (ex: java.lang.Exception) {
                throw ex
            }
        }

    }

    fun retrieveNewsDetail(mNewsId: Int): Flowable<News>? {
        return storageDataSource?.retrieveNewsDetail(mNewsId)
    }

}
