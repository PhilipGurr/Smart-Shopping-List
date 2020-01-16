package com.philipgurr.smartshoppinglist.di.modules

import com.philipgurr.smartshoppinglist.ui.addproduct.CameraFragment
import com.philipgurr.smartshoppinglist.ui.completedlists.CompletedListsFragment
import com.philipgurr.smartshoppinglist.ui.detail.ListDetailFragment
import com.philipgurr.smartshoppinglist.ui.mylists.MyListsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeShoppingListFragment(): MyListsFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeShoppingListDetailFragment(): ListDetailFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeCompletedShoppingListFragment(): CompletedListsFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeCameraFragment(): CameraFragment
}