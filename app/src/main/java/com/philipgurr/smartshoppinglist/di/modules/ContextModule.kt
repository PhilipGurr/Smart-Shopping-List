package com.philipgurr.smartshoppinglist.di.modules

import android.content.Context
import com.philipgurr.smartshoppinglist.App
import dagger.Module
import dagger.Provides

@Module
class ContextModule {
    @Provides
    fun provideContext(app: App): Context = app.applicationContext
}