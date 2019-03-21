package com.angelsheaven.teremdemoapp.utilities

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.angelsheaven.teremdemoapp.AppExecutors
import com.angelsheaven.teremdemoapp.data.Repository
import com.angelsheaven.teremdemoapp.data.database.AppDatabase
import com.angelsheaven.teremdemoapp.data.network.NetworkDataSource
import com.angelsheaven.teremdemoapp.data.storage.StorageDataSource
import com.angelsheaven.teremdemoapp.ui.MainActivity
import com.angelsheaven.teremdemoapp.ui.MainActivityViewModelFactory
import com.angelsheaven.teremdemoapp.ui.listNews.newNews.ListNewsFragmentViewModelFactory
import com.angelsheaven.teremdemoapp.ui.listNews.savedNews.ListSavedNewsFragmentViewModelFactory
import com.angelsheaven.teremdemoapp.ui.login.LoginFragmentViewModelFactory
import com.angelsheaven.teremdemoapp.ui.viewNewsDetail.ViewNewsDetailFragmentViewModelFactory


fun provideRepository(context: Context, activity: Activity? = null): Repository? {
    val database: AppDatabase? = AppDatabase.getInstance(context.applicationContext)
    val executors: AppExecutors? = AppExecutors.getInstance()
    val networkDataSource = NetworkDataSource.getInstance(
        context.applicationContext
        , executors
    )
    val storageDataSource = StorageDataSource
        .getInstance(
            database, context.applicationContext
            , executors
        )
    return Repository.getInstance(networkDataSource, storageDataSource, activity)
}

fun provideMainActivityViewModelFactory(
    context: Context,
    activity: MainActivity
): MainActivityViewModelFactory? {
    val repository: Repository? = provideRepository(context, activity)
    return repository?.let {
        MainActivityViewModelFactory(context, it)
    }
}

fun provideViewNewsDetailFragmentViewModelFactory(
    context: Context
    , activity: Activity
): ViewNewsDetailFragmentViewModelFactory? {
    val repository = provideRepository(context, activity)
    return repository?.let {
        ViewNewsDetailFragmentViewModelFactory(context, it)
    }
}

fun provideListSavedNewsFragmentViewModelFactory(
    context: Context
    , activity: Activity
): ListSavedNewsFragmentViewModelFactory? {
    val repository = provideRepository(context, activity)
    return repository?.let {
        ListSavedNewsFragmentViewModelFactory(context, it)
    }
}

fun provideListNewsFragmentViewModelFactory(
    context: Context,
    activity: Activity
): ListNewsFragmentViewModelFactory? {
    val repository = provideRepository(context, activity)
    return repository?.let {
        ListNewsFragmentViewModelFactory(context, it)
    }
}

fun provideLoginFragmentViewModelFactory(
    context: Context,
    activity: Activity,
    fragment: Fragment
): LoginFragmentViewModelFactory?{
    val repository = provideRepository(context,activity)
    return repository?.let {
        LoginFragmentViewModelFactory(context,it,fragment)
    }
}