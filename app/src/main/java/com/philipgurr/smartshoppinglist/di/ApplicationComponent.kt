package com.philipgurr.smartshoppinglist.di

import com.philipgurr.data.di.DataComponent
import com.philipgurr.smartshoppinglist.App
import com.philipgurr.smartshoppinglist.di.modules.AndroidModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidModule::class
    ],
    dependencies = [
        DataComponent::class
    ]
)
interface ApplicationComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: App): Builder

        fun dataComponent(dataComponent: DataComponent): Builder
        fun build(): ApplicationComponent
    }
}