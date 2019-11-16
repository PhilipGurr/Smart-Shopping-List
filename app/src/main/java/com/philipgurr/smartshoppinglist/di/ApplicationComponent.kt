package com.philipgurr.smartshoppinglist.di

import com.philipgurr.smartshoppinglist.App
import com.philipgurr.smartshoppinglist.di.modules.AndroidModule
import com.philipgurr.smartshoppinglist.di.modules.RepositoryModule
import com.philipgurr.smartshoppinglist.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AndroidModule::class,
    RepositoryModule::class
])
interface ApplicationComponent : AndroidInjector<App> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<App> {
        override fun create(@BindsInstance app: App): ApplicationComponent
    }
}