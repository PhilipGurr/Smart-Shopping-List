package com.philipgurr.smartshoppinglist.di.modules

import android.content.Context
import com.philipgurr.smartshoppinglist.App
import dagger.Binds
import dagger.Module

@Module
interface ContextModule {
    @Binds
    fun bindContext(app: App): Context
}