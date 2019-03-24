package com.angelsheaven.teremdemoapp.ui.viewNewsDetail

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angelsheaven.teremdemoapp.data.Repository
import com.angelsheaven.teremdemoapp.data.storage.News
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import io.reactivex.Flowable


class ViewNewsDetailFragmentViewModel(
    val context: Context,
    private val repository: Repository
) : ViewModel(), MyLogger{

    val newsDetail = ObservableField<News>(News())

    fun getNewsDetailObservable(newsId: Int): Flowable<News>? {
        return repository?.retrieveNewsDetail(newsId)
    }



}

class ViewNewsDetailFragmentViewModelFactory(
    private val context: Context
    , private val repository: Repository
) : ViewModelProvider
.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewNewsDetailFragmentViewModel(context, repository) as T
    }
}
