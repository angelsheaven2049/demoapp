package com.angelsheaven.teremdemoapp.ui.viewNewsDetail

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angelsheaven.teremdemoapp.MyApplication
import com.angelsheaven.teremdemoapp.data.NewsRepository
import com.angelsheaven.teremdemoapp.data.storage.News
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import io.reactivex.Flowable
import javax.inject.Inject


class ViewNewsDetailFragmentViewModel: ViewModel(), MyLogger{

    @Inject lateinit var repository:NewsRepository
    @Inject lateinit var context: Context

    init {
        MyApplication.appComponent.inject(this)
    }

    val newsDetail = ObservableField<News>(News())

    fun getNewsDetailObservable(newsId: Int): Flowable<News>? {
        return repository?.retrieveNewsDetail(newsId)
    }



}

class ViewNewsDetailFragmentViewModelFactory: ViewModelProvider
.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewNewsDetailFragmentViewModel() as T
    }
}
