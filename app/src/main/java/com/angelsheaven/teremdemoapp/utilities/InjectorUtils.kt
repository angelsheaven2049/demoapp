package com.angelsheaven.teremdemoapp.utilities

import androidx.fragment.app.Fragment
import com.angelsheaven.teremdemoapp.ui.MainActivityViewModelFactory
import com.angelsheaven.teremdemoapp.ui.listNews.newNews.ListNewsFragmentViewModelFactory
import com.angelsheaven.teremdemoapp.ui.listNews.savedNews.ListSavedNewsFragmentViewModelFactory
import com.angelsheaven.teremdemoapp.ui.login.LoginFragmentViewModelFactory
import com.angelsheaven.teremdemoapp.ui.viewNewsDetail.ViewNewsDetailFragmentViewModelFactory


fun provideMainActivityViewModelFactory(): MainActivityViewModelFactory? {
    return MainActivityViewModelFactory()
}

fun provideViewNewsDetailFragmentViewModelFactory(
): ViewNewsDetailFragmentViewModelFactory? {

   return ViewNewsDetailFragmentViewModelFactory()
}

fun provideListSavedNewsFragmentViewModelFactory() : ListSavedNewsFragmentViewModelFactory? {
    return ListSavedNewsFragmentViewModelFactory()
}

fun provideListNewsFragmentViewModelFactory(): ListNewsFragmentViewModelFactory? {
    return ListNewsFragmentViewModelFactory()
}

fun provideLoginFragmentViewModelFactory(
    fragment: Fragment
): LoginFragmentViewModelFactory?{
    return LoginFragmentViewModelFactory(fragment)
}
