package com.angelsheaven.teremdemoapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angelsheaven.teremdemoapp.data.Repository
import com.angelsheaven.teremdemoapp.utilities.MyLogger

/**
 * {@link ViewModel} for {@link MainActivity}
 */
class MainActivityViewModel(
    val context: Context
    , private val repository: Repository
) : ViewModel(), MyLogger {

    val networkConnectionState by lazy { repository.networkConnectionState }

}

class MainActivityViewModelFactory(
    private val mContext: Context
    , private val mRepository: Repository
) : ViewModelProvider
.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(mContext, mRepository) as T
    }
}
