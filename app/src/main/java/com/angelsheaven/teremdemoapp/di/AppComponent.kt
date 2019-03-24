package com.angelsheaven.teremdemoapp.di

import com.angelsheaven.teremdemoapp.ui.MainActivityViewModel
import com.angelsheaven.teremdemoapp.ui.listNews.newNews.ListNewsFragmentViewModel
import com.angelsheaven.teremdemoapp.ui.listNews.savedNews.ListSavedNewsFragmentViewModel
import com.angelsheaven.teremdemoapp.ui.login.LoginFragmentViewModel
import com.angelsheaven.teremdemoapp.ui.viewNewsDetail.ViewNewsDetailFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class,StorageModule::class,NetworkModule::class])
@Singleton interface AppComponent{
    fun inject(mainActivityViewModel: MainActivityViewModel)

    fun inject(viewNewsDetailFragmentViewModel: ViewNewsDetailFragmentViewModel)

    fun inject(listNewsFragmentViewModel: ListNewsFragmentViewModel)

    fun inject(listSavedNewsFragmentViewModel: ListSavedNewsFragmentViewModel)

    fun inject(listLoginFragmentViewModel: LoginFragmentViewModel)

}
