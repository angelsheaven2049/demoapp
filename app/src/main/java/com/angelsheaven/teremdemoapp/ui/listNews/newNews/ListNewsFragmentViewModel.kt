package com.angelsheaven.teremdemoapp.ui.listNews.newNews

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.angelsheaven.teremdemoapp.*
import com.angelsheaven.teremdemoapp.data.NewsRepository
import com.angelsheaven.teremdemoapp.data.NewsSearchResult
import com.angelsheaven.teremdemoapp.data.storage.News
import com.angelsheaven.teremdemoapp.data.storage.convertFilterIndexOptionToStringValue
import com.angelsheaven.teremdemoapp.data.storage.convertSortIndexOptionToStringValue
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListNewsFragmentViewModel: ViewModel(), MyLogger {

    @Inject lateinit var repository:NewsRepository
    @Inject lateinit var context: Context

    init {
        MyApplication.appComponent.inject(this)
    }

    private val queryLiveData = MutableLiveData<Bundle>()

    val isCompleteReloadingData by lazy { MutableLiveData<Boolean>() }

    private val newsResult: LiveData<NewsSearchResult> = Transformations.map(queryLiveData) {
        repository.search(
            convertFilterIndexOptionToStringValue(it[FILTER_VALUE] as Int)
            , convertSortIndexOptionToStringValue(it[SORT_VALUE] as Int)
            , it[QUERY_VALUE] as String
        ) { isSuccess ->
            isCompleteReloadingData.postValue(isSuccess)
        }
    }

    internal var searchCondition: Bundle? = null

    internal val news: LiveData<PagedList<News>> = Transformations.switchMap(newsResult) {
        it.data
    }

    internal val networkErrors: LiveData<String> = Transformations.switchMap(newsResult) {
        it.networkErrors
    }

    @Synchronized
    fun loadNews() {
        queryLiveData.postValue(searchCondition)
    }

    fun setQueryString(queryString: String?) {
        searchCondition?.apply {
            this.putString(QUERY_VALUE, queryString)
        }
    }

    fun setFilterOptionIndex(filterOptionIndex: Int) {
        searchCondition?.apply {
            this.putInt(FILTER_VALUE, filterOptionIndex)
        }
    }

    fun setSortOptionIndex(sortOptionIndex: Int) {
        searchCondition?.apply {
            this.putInt(SORT_VALUE, sortOptionIndex)
        }
    }

    fun getFilterOptionIndex() = searchCondition?.getInt(FILTER_VALUE) ?: DEFAULT_FILTER_OPTION_INDEX

    fun getSortOptionIndex() = searchCondition?.getInt(SORT_VALUE) ?: DEFAULT_SORT_OPTION_INDEX

    fun lastQueryValue(): Bundle = queryLiveData.value ?: defaultSearchCondition

    fun markReadNews(news: News?) {
        viewModelScope.launch {
            val markReadNewsTask = async { repository.markNewsRead(news) }

            val resultMarkReadNewsTask = markReadNewsTask.await()

            log("Result mark read news tak $resultMarkReadNewsTask")
        }
    }

    fun bookmarkNews(news: News?) {
        news?.run {
            viewModelScope.launch {
                val bookmarkTask = async { repository.updateNewsFieldSaved(news, true) }

                log("Insert task result is ${bookmarkTask.await()}")
            }
        }
    }

    fun unBookmarkNews(news: News?) {
        news?.run {
            viewModelScope.launch {
                val unbookmarkTask = async { repository.updateNewsFieldSaved(news, false) }

                log("unbookmark task result ${unbookmarkTask.await()}")
            }
        }
    }

    fun refreshNews() {
        viewModelScope.launch {
            repository.retrieveNewNews()
        }
    }


}

class ListNewsFragmentViewModelFactory : ViewModelProvider
.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListNewsFragmentViewModel() as T
    }
}