package com.angelsheaven.teremdemoapp.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.angelsheaven.teremdemoapp.data.Repository
import com.angelsheaven.teremdemoapp.data.storage.AppDatabase
import com.angelsheaven.teremdemoapp.data.network.NetworkDataSource
import com.angelsheaven.teremdemoapp.data.storage.StorageDataSource
import com.angelsheaven.teremdemoapp.ui.MainActivityViewModelFactory
import com.angelsheaven.teremdemoapp.ui.listNews.newNews.ListNewsFragmentViewModelFactory
import com.angelsheaven.teremdemoapp.ui.listNews.savedNews.ListSavedNewsFragmentViewModelFactory
import com.angelsheaven.teremdemoapp.ui.login.LoginFragmentViewModelFactory
import com.angelsheaven.teremdemoapp.ui.viewNewsDetail.ViewNewsDetailFragmentViewModelFactory


fun provideRepository(context: Context,isItInitializedData:Boolean?): Repository? {
    val database: AppDatabase? = AppDatabase.getInstance(context.applicationContext)
    val networkDataSource = NetworkDataSource.getInstance()
    val storageDataSource = StorageDataSource
        .getInstance(database)
    return Repository.getInstance(networkDataSource, storageDataSource,isItInitializedData)
}

fun provideMainActivityViewModelFactory(
    context: Context
    ,isItInitializedData:Boolean?
): MainActivityViewModelFactory? {
    val repository: Repository? = provideRepository(context,isItInitializedData)
    return repository?.let {
        MainActivityViewModelFactory(context, it)
    }
}

fun provideViewNewsDetailFragmentViewModelFactory(
    context: Context,
    isItInitializedData:Boolean
): ViewNewsDetailFragmentViewModelFactory? {
    val repository = provideRepository(context,isItInitializedData)
    return repository?.let {
        ViewNewsDetailFragmentViewModelFactory(context, it)
    }
}

fun provideListSavedNewsFragmentViewModelFactory(
    context: Context,
    isItInitializedData:Boolean
): ListSavedNewsFragmentViewModelFactory? {
    val repository = provideRepository(context,isItInitializedData)
    return repository?.let {
        ListSavedNewsFragmentViewModelFactory(context, it)
    }
}

fun provideListNewsFragmentViewModelFactory(
    context: Context,
    isItInitializedData:Boolean
): ListNewsFragmentViewModelFactory? {
    val repository = provideRepository(context,isItInitializedData)
    return repository?.let {
        ListNewsFragmentViewModelFactory(context, it)
    }
}

fun provideLoginFragmentViewModelFactory(
    context: Context,
    isItInitializedData:Boolean?,
    fragment: Fragment
): LoginFragmentViewModelFactory?{
    val repository = provideRepository(context,isItInitializedData)
    return repository?.let {
        LoginFragmentViewModelFactory(context,it,fragment)
    }
}