package com.angelsheaven.teremdemoapp

import com.angelsheaven.teremdemoapp.ui.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton interface AppComponent{
    fun inject(mainActivityViewModel: MainActivityViewModel)
}
