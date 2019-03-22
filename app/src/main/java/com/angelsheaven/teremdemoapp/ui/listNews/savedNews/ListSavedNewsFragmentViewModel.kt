package com.angelsheaven.teremdemoapp.ui.listNews.savedNews

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.angelsheaven.teremdemoapp.data.Repository
import com.angelsheaven.teremdemoapp.data.News
import com.angelsheaven.teremdemoapp.data.storage.NewsSearchResult
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ListSavedNewsFragmentViewModel(
    val context: Context,
    private val repository: Repository
): ViewModel(),MyLogger{
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

class ListSavedNewsFragmentViewModelFactory(
    private val context: Context,
    private val repository: Repository): ViewModelProvider
.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListSavedNewsFragmentViewModel(context,repository) as T
    }
}


