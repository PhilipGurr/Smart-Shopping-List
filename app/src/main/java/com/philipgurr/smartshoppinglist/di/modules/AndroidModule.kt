package com.philipgurr.smartshoppinglist.di.modules

import com.philipgurr.smartshoppinglist.ui.addproduct.AddProductFragment
import com.philipgurr.smartshoppinglist.ui.addproduct.CameraFragment
import com.philipgurr.smartshoppinglist.ui.completedshoppinglist.CompletedShoppingListFragment
import com.philipgurr.smartshoppinglist.ui.shoppinglist.detail.ShoppingListDetailFragment
import com.philipgurr.smartshoppinglist.ui.shoppinglist.main.ShoppingListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidModule {
    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeShoppingListFragment(): ShoppingListFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeShoppingListDetailFragment(): ShoppingListDetailFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeCompletedShoppingListFragment(): CompletedShoppingListFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeAddProductFragment(): AddProductFragment

    @ContributesAndroidInjector(modules = [ViewModelModule::class])
    abstract fun contributeCameraFragment(): CameraFragment
}