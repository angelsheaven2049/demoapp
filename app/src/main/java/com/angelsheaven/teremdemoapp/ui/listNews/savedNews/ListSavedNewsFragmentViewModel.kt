package com.angelsheaven.teremdemoapp.ui.listNews.savedNews

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.angelsheaven.teremdemoapp.MyApplication
import com.angelsheaven.teremdemoapp.data.NewsRepository
import com.angelsheaven.teremdemoapp.data.NewsSearchResult
import com.angelsheaven.teremdemoapp.data.storage.News
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListSavedNewsFragmentViewModel: ViewModel(),MyLogger{

    @Inject lateinit var repository:NewsRepository
    @Inject lateinit var context: Context

    init {
        MyApplication.appComponent.inject(this)
    }

    private val querySavedLiveData = MutableLiveData<Bundle>()

    private val savedNewsResult: LiveData<NewsSearchResult> = Transformations.map(querySavedLiveData) {
        repository.getSavedNews()

    }

    internal val savedNews: LiveData<PagedList<News>> = Transformations.switchMap(savedNewsResult) {
        it.data
    }

    fun loadSavedNews() {
        querySavedLiveData.postValue(null)
    }

    fun unBookmarkNews(news: News?) {
        news?.run {
            viewModelScope.launch {
                val unbookmarkTask = async { repository.updateNewsFieldSaved(news,false) }

                val resultUnbookmarkTask = unbookmarkTask.await()

                log("Unbookmark news $resultUnbookmarkTask")
            }
        }
    }


}

class ListSavedNewsFragmentViewModelFactory: ViewModelProvider
.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListSavedNewsFragmentViewModel() as T
    }
}


