package com.philipgurr.smartshoppinglist.di.modules

import com.philipgurr.smartshoppinglist.ui.completedshoppinglist.CompletedShoppingListFragment
import com.philipgurr.smartshoppinglist.ui.shoppinglist.ShoppingListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeShoppingListFragment(): ShoppingListFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeCompletedShoppingListFragment(): CompletedShoppingListFragment
}