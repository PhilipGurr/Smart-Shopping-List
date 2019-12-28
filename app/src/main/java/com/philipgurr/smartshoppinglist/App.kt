package com.philipgurr.smartshoppinglist

import com.philipgurr.data.di.DaggerDataComponent
import com.philipgurr.data.di.modules.ContextModule
import com.philipgurr.smartshoppinglist.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {
    private val dataComponent by lazy {
        DaggerDataComponent
            .builder()
            .contextModule(ContextModule(this))
            .build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent
            .builder()
            .application(this)
            .dataComponent(dataComponent)
            .build()
    }
}