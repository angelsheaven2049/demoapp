package com.angelsheaven.teremdemoapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angelsheaven.teremdemoapp.MyApplication
import com.angelsheaven.teremdemoapp.data.NewsRepository
import com.angelsheaven.teremdemoapp.utilities.MyLogger
import javax.inject.Inject

/**
 * {@link ViewModel} for {@link MainActivity}
 */
class MainActivityViewModel: ViewModel(), MyLogger {

    @Inject lateinit var repository:NewsRepository
    @Inject lateinit var context: Context

    init {
        MyApplication.appComponent.inject(this)
    }

    val networkConnectionState by lazy { repository.networkConnectionState }

    val updateInitializeDataState by lazy { repository.updateInitializeData }


}

class MainActivityViewModelFactory : ViewModelProvider
.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel() as T
    }
}
